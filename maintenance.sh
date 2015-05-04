#!/usr/bin/env bash -e

#############################
# Define
#############################
commands=(
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

echo "List of 'targetSdkVersion':"
for data in `grep -r "targetSdkVersion\s" .`; do
  if [ -z `echo $data |grep "targetSdkVersion 22"` ]; then
    # 22じゃなかった時
    echo "Invalid: $data"
  fi
done

echo "List of 'minSdkVersion':"
for data in `grep -r "minSdkVersion\s" .`; do
  if [ -z `echo $data |grep "minSdkVersion 10"` ]; then
    # 22じゃなかった時
    echo "Invalid: $data"
  fi
done


echo "List of 'buildToolsVersion':"
for data in `grep -r "buildToolsVersion\s" .`; do
  if [ -z `echo $data |grep "buildToolsVersion \"21.1.2\""` ]; then
    # 22じゃなかった時
    echo "Invalid: $data"
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
  pushd $parentDir
  ./gradlew clean assembleDebug
  popd
done

IFS=$tmpIFS
}

for arg in $@; do
  for availCmd in ${commands[@]}; do
    if [ $arg == $availCmd ]; then
      echo -ne "\e[36m" # cyan
      echo -ne "Execute: $availCmd task"
      echo -e "\e[0m" # reset
      echo
      $availCmd
    fi
  done
done


