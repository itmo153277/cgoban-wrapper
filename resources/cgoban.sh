#!/bin/sh
script_dir=$(dirname "$0")
exec java -Xmx200m -Xms32m -Xmn2m -Djava.net.preferIPv4Stack=true -Dawt.useSystemAAFontSettings=gasp -jar "$script_dir/cgoban_wrapper.jar" "$script_dir/cgoban.jar" "$@"
