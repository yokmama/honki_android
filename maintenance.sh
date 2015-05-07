#!/usr/bin/env bash -e

#############################
# Define
#############################
commands=(
cleanall
check
buildall
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
echo -ne $'\e[31m' # cyan
echo -ne "Invalid: "
echo -ne $'\e[0m' # reset
echo "$1"
}

echo "List of 'targetSdkVersion':"
for data in `grep -r "targetSdkVersion\s" .`; do
  if [ -z `echo $data |grep "targetSdkVersion 22"` ]; then
    # 22じゃなかった時
    outInvalid $data
    #echo "Invalid: $data"
  fi
done

echo
echo "List of 'minSdkVersion':"
for data in `grep -r "minSdkVersion\s" .`; do
  if [ -z `echo $data |grep "minSdkVersion 10\|minSdkVersion 14"` ]; then
    # 10じゃなかった時
    outInvalid $data
  fi
done

echo
echo "List of 'buildToolsVersion':"
for data in `grep -r "buildToolsVersion\s" .`; do
  if [ -z `echo $data |grep "buildToolsVersion \"22.0.1\""` ]; then
    # 21.1.2じゃなかった時
    outInvalid $data
  fi
done

echo
echo "List of 'ActionBarActivity':"
for data in `grep -r "\sActionBarActivity" .`; do
  # ActionBarActivity が見つかった時
  outInvalid $data
done

echo
echo "List of 'appcompat-v7':"
for data in `grep -r "com.android.support:appcompat-v7" .`; do
  # ActionBarActivity が見つかった時
  if [ -z `echo $data |grep "com.android.support:appcompat-v7:22.1.1"` ]; then
    # 22.1.1じゃない場合
    outInvalid $data
  fi
done

echo
echo "注意: maintainance.shファイル自身が引っかかることがあることがありますが無視してください。"

IFS=$tmpIFS
}

#############################

function buildall() {
tmpIFS=$IFS
IFS=$'\n'

for gradlewFile in `find . -type f -name gradlew`; do
  parentDir=${gradlewFile%/*}
  pushd $parentDir
  ./gradlew --daemon clean assembleDebug
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


