#!/bin/bash

# Ermittelt den Pfad des aktuellen Skripts
BASE_DIR="$(dirname "$(realpath "$0")")"

# Definiere den relativen Pfad zur JavaFX-Bibliothek (unabhängig vom Betriebssystem)
JAVA_FX_PATH="$BASE_DIR/lib"

# Überprüfen, ob der JavaFX-Bibliothekspfad existiert
if [ ! -d "$JAVA_FX_PATH" ]; then
    echo "JavaFX-Bibliothekspfad nicht gefunden: $JAVA_FX_PATH"
    exit 1
fi

# Überprüfen, ob die JAR-Datei existiert
JAR_FILE="$BASE_DIR/Tiny-PomoPal.jar"
if [ ! -f "$JAR_FILE" ]; then
    echo "JAR-Datei nicht gefunden: $JAR_FILE"
    exit 1
fi

# Betriebssystem erkennen
OS_TYPE="$(uname)"
if [[ "$OS_TYPE" == "Linux" ]]; then
    JAVA_CMD="java"
elif [[ "$OS_TYPE" == "MINGW"* || "$OS_TYPE" == "CYGWIN"* || "$OS_TYPE" == "MSYS"* ]]; then
    JAVA_CMD="java.exe"
else
    echo "Nicht unterstütztes Betriebssystem: $OS_TYPE"
    exit 1
fi

# Starte die Anwendung
echo "Starte Anwendung..."
"$JAVA_CMD" --module-path "$JAVA_FX_PATH" --add-modules javafx.controls,javafx.fxml --enable-preview -jar "$JAR_FILE"

