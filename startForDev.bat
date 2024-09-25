@echo off
chcp 65001
cls
cd /d %~dp0

:main
    call :output "非常好脚本，使我的项目运行"
    call :input "proxy" "设置代理（回车跳过）："
    if "%proxy%"=="" (
        call :output "不设置代理"
    ) else (
        call :output "设置代理为： %proxy%"
        set http_proxy=%proxy%
        set https_proxy=%proxy%
        @REM cmd /C gradlew systemProp.http.proxyHost=%proxy% systemProp.http.proxyPort=1080 systemProp.https.proxyHost=%proxy% systemProp.https.proxyPort=1080
    )

    call :output "开始运行项目 1/2……"
    call :output
    cmd /C gradlew runData
    call :output
    call :input "username" "设置用户名（回车跳过）: "
    if "%username%"=="" (
        call :output "不设置用户名"
        call :output "开始运行项目 2/2……"
        call :output
        cmd /C gradlew runClient
    ) else (
        call :output "设置用户名为： %username%"
        call :output "开始运行项目 2/2……"
        call :output
        cmd /C gradlew runClient --args="--username %username%"
    )
    exit /b

:output
set count=0
for %%i in (%*) do (
    set /a count+=1
    if "%%~i"=="" (
        echo.
    ) else (
        echo %%~i
    )
)
if %count%==0 echo.
exit /b

:input
    more +1 < nul
    set "%~1="
    set /p "%~1=%2"
    exit /b