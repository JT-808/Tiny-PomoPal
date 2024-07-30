#!/bin/bash

# insert JavaFX lib path
JAVA_FX_PATH="/path/to/javafx-sdk-21.0.1/lib"

# start app
java --module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml --enable-preview -jar Tiny-PomoPal.jar






