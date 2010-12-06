#!/bin/sh

cd `dirname $0`

export CLASSPATH="$GWT_HOME/gwt-dev.jar:$GWT_HOME/gwt-user.jar"

if [ -z "$GWT_HOME" ] ; then
	echo "GWT_HOME is not set";
	exit 1;
fi


for c in `find src/gpsweb/client/views -maxdepth 1 -name \*.css`; do
	name=`basename $c .css`;
	java com.google.gwt.resources.css.InterfaceGenerator -standalone -typeName gpsweb.client.views."$name"Css -css $c > src/gpsweb/client/views/"$name"Css.java
done
