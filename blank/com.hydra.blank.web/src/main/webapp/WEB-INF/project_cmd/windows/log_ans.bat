@echo off
grep Exception e:/anslog/%1.log | grep -v by | awk "{print $3, $2}"
@echo on
