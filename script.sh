#!/bin/bash

# Pfad zur JavaFX SDK
JAVA_FX_PATH="/home/woodz/Dev/Libs/javafx-sdk-21.0.1/lib"

# Anwendung starten
java --module-path $JAVA_FX_PATH --add-modules javafx.controls,javafx.fxml --enable-preview -jar Tiny-PomoPal.jar






