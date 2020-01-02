@echo off
rem HTML5 Core v1.3
rem This script will build unsigned Android app
set JAVA_HOME=C:\Program Files (x86)\Java\jdk1.7.0_79\
set path=%path%;F:\SDK data\apache-ant-1.9.4-bin\apache-ant-1.9.4\bin
ant release
