#!/bin/bash

echo "===================================="
echo "   HE THONG QUAN LY DIEM SINH VIEN"
echo "===================================="
echo
echo "Dang compile du an..."

# Create target directory if it doesn't exist
mkdir -p target/classes

# Compile all Java files
javac -cp "lib/*" -d target/classes src/main/java/connection/*.java src/main/java/Model/*.java src/main/java/View/*.java src/main/java/Controller/*.java

if [ $? -ne 0 ]; then
    echo
    echo "[LOI] Khong the compile du an!"
    echo "Vui long kiem tra lai ma nguon."
    exit 1
fi

echo "Compile thanh cong!"
echo
echo "Dang khoi dong ung dung..."
echo

# Run the application
java -cp "target/classes:lib/*" View.Main

echo
echo "Ung dung da dong."