#!/usr/bin/env bash

ok() { echo -e "\033[32m[+] $*\033[0m"; }      # green

./gradlew assembleDebug
echo ""
ok "Installing..."
adb install -r app/build/outputs/apk/debug/app-debug.apk
