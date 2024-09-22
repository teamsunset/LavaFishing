@echo off
chcp 65001
cls
cd /d %~dp0
echo 非常好脚本，使我的项目运行
set isSetUser=
set /p isSetUser="要设置用户名吗？ y/n: "
set username=
if "%isSetUser%"=="y" (
    echo 设置用户名
    set /p username="请输入用户名: "
)
echo 不设置用户名
echo 开始运行项目……
echo,
if "%username%"=="" (
    gradlew runClientAfterRunData
) else (
    gradlew runClientAfterRunData --args="--username %username%"
)



