#!/bin/sh

find . -iname "*.css" | while read i; do sed -i -e 's/\s*$//g' "$i"; done
find . -iname "*.ui.xml" | while read i; do sed -i -e 's/\s*$//g' "$i"; done
