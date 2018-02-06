@echo off
grep -e %1 e:/anslog/* | awk "{print substr($1, 11, 8), $2}"
@echo on
