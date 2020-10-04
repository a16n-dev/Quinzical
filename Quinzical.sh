#!/bin/bash

# This script runs the "quinzical.jar" file using the run cnfigurations for the
# se2062020 VirtualBox image.

# Author: Team 26

java --module-path /home/se2062020/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.media,javafx.base,javafx.fxml -jar quinzical.jar &

disown

exit 