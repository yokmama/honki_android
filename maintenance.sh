#!/usr/bin/env bash -e

#############################
# Define
#############################
commands=(
cleanall
check
buildall
collectapk
lintallNoError
)
scriptDir="$(cd "$(dirname "${BASH_SOURCE:-${(%):-%N}}")"; pwd)"
cd $scriptDir


#############################
# core
#############################

if [ $# -eq 0 ]; then
  echo "Require arg: ./.maintenance.sh [subcommand]"
  echo "Subcommands:"
  echo -n "   "
  for command in ${commands[@]}; do
    echo -n " $command"
  done
  echo
  exit 0
fi

#############################

function check() {
tmpIFS=$IFS
IFS=$'\n'

function outInvalid() {
if [ -z `echo $1 |grep "^./.git/\|^./maintenance\.sh\|^Binary\sfile\s\./"` ]; then
  #echo -ne $'\e[31m' # cyan
  echo -ne "Invalid: "
  #echo -ne $'\e[0m' # reset
  echo "$1"
fi
}

echo "Check targetSdkVersion(22) :"
for data in `grep -r "targetSdkVersion\s" .`; do
  if [ -z `echo $data |grep "targetSdkVersion 22"` ]; then
    # 22じゃなかった時
    outInvalid $data
    #echo "Invalid: $data"
  fi
done

echo
echo "Check minSdkVersion(10 or 15) :"
for data in `grep -r "minSdkVersion\s" .`; do
  if [ -z `echo $data |grep "minSdkVersion 10\|minSdkVersion 15"` ]; then
    # 10じゃなかった時
    outInvalid $data
  fi
done

echo
echo "Check buildToolsVersion(22.0.1) :"
for data in `grep -r "buildToolsVersion\s" .`; do
  if [ -z `echo $data |grep "buildToolsVersion \"22.0.1\""` ]; then
    # 21.1.2じゃなかった時
    outInvalid $data
  fi
done

echo
echo "Check exists 'ActionBarActivity' :"
for data in `grep -r "\sActionBarActivity" .`; do
  # ActionBarActivity が見つかった時
  outInvalid $data
done

echo
echo "Check appcompat-v7 version(22.1.1) :"
for data in `grep -r "compile\s'com.android.support:appcompat-v7" .|grep -v "/build/outputs/"`; do
  if [ -z `echo $data |grep "com.android.support:appcompat-v7:22.1.1"` ]; then
    # 22.1.1じゃない場合
    outInvalid $data
  fi
done

echo
echo "Check Gradle version(1.2.3) :"
for data in `grep -r "com.android.tools.build:gradle" .`; do
  if [ -z `echo $data |grep "com.android.tools.build:gradle:1.2.3"` ]; then
    # 22.1.1じゃない場合
    outInvalid $data
  fi
done

IFS=$tmpIFS
}

#############################

function buildall() {
tmpIFS=$IFS
IFS=$'\n'

for gradlewFile in `find . -type f -name gradlew`; do
  parentDir=${gradlewFile%/*}
  if [ `echo $parentDir|grep Chapter07/Lesson33/before` ]; then
    # 例外。既知のビルド出来ないプロジェクト。
    continue;
  elif [ `echo $parentDir|grep Chapter06/Lesson28/before` ]; then
    # 例外。既知のビルド出来ないプロジェクト。
    continue;
  fi
  pushd $parentDir
  ./gradlew --info clean assembleDebug
  popd
done

IFS=$tmpIFS
}

#############################

function cleanall() {
tmpIFS=$IFS
IFS=$'\n'

for gradlewFile in `find . -type f -name gradlew`; do
  parentDir=${gradlewFile%/*}
  pushd $parentDir
  ./gradlew --daemon clean
  popd
done

IFS=$tmpIFS
}

#############################

function collectapk() {
if [ -z ${WORKSPACE} ]; then
  echo "Not env on Jenkins"
  exit 1
fi
local resultDir="${WORKSPACE}/result"

for gradlewFile in `find . -type f -name gradlew`; do
  parentDir=${gradlewFile%/*}
  pushd $parentDir > /dev/null
  if [ -f ./app/build/outputs/apk/app-debug.apk ]; then
    mkdir -p $resultDir/$parentDir
    cp ./app/build/outputs/apk/app-debug.apk $resultDir/$parentDir/
  elif [ -f ./android/build/outputs/apk/android-debug.apk ]; then
    mkdir -p $resultDir/$parentDir
    cp ./android/build/outputs/apk/android-debug.apk $resultDir/$parentDir/
  else
    echo "Not found apk: $parentDir"
  fi
  popd > /dev/null
done
}

#############################

function lintallNoError() {
tmpIFS=$IFS
IFS=$'\n'

for gradlewFile in `find . -type f -name gradlew`; do
  parentDir=${gradlewFile%/*}
  pushd $parentDir
  ./gradlew --info lint || true
  popd
done

IFS=$tmpIFS
}


#############################

for arg in $@; do
  for availCmd in ${commands[@]}; do
    if [ $arg == $availCmd ]; then
      echo
      echo -ne $'\e[36m' # cyan
      echo -ne "Execute: $availCmd task"
      echo -e $'\e[0m' # reset
      echo
      $availCmd
    fi
  done
done


