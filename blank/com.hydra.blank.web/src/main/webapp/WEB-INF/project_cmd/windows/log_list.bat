@echo off
ls e:/anslog | sort | tail -10 | awk -F . "{print $1}"
@echo on
