@echo off
ls e:/anslog/ans_dir | awk -F . "{print $1}"
@echo on
