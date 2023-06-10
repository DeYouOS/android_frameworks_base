#!/system/bin/sh
#
# Copyright (C) 2016 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

DATA_APK=/data/app

function appinstall() {
    if [ -d $1 ];then
        files=$(ls $1/**/*.apk)
        for file in ${files};do
            log -p i -t preload_appinstall "install: ${file}"
            pm install ${file}
        done
    fi
}

#获取是否已经预安装过标记位
PREINSTALL_RESULT=`getprop persist.sys.preinstall.value`
if [ -z "${PREINSTALL_RESULT}" ]; then
    appinstall "/system/preload-app"
    appinstall "/vendor/preload-app"
    setprop persist.sys.preinstall.value 1
fi
