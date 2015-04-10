#!/usr/bin/env bash -e

commands=(
check
)

if [ $# -eq 0 ]; then
  echo "Require arg: ./.maintenance.sh [subcommand]"
  echo "Subcommands:"
  echo -n "   "
  for command in ${commands[@]}; do
    echo " $command"
  done
  echo
  exit 0
fi

function check() {
echo "List of 'targetSdkVersion':"
grep -r "targetSdkVersion\s" .

echo "List of 'targetSdkVersion':"
grep -r "targetSdkVersion\s" .

echo "List of 'minSdkVersion':"
grep -r "minSdkVersion\s" .

echo "List of 'buildToolsVersion':"
grep -r "buildToolsVersion\s" .

echo "List of 'project-jdk-name':"
grep -r "project-jdk-name=\s" .

}

for command in ${commands[@]}; do
  echo " $command"
  if [ $1 = "check" ]; then
    check
  fi
done


