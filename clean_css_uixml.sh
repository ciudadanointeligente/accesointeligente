#!/bin/sh

find . -iname "*.css" | while read i; do sed -e "s/\s*$//g" "$i" > "$i.2" ;mv "$i.2" "$i" ; done
find . -iname "*.ui.xml" | while read i; do sed -e "s/\s*$//g" "$i" > "$i.2" ;mv "$i.2" "$i" ; done
