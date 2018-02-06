@echo off
grep Exception e:/anslog/* | awk "{print $3}" | sort | uniq | grep -v by
@echo on
