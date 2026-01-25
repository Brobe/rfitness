#!/usr/bin/env bash

./gradlew assembleDebug
echo ""
echo "Installing..."
adb install -r app/build/outputs/apk/debug/app-debug.apk
