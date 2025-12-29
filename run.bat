@echo off
echo ====================================
echo   HE THONG QUAN LY DIEM SINH VIEN
echo ====================================
echo.
echo Dang compile du an...

REM Create target directory if it doesn't exist
if not exist "target\classes" mkdir "target\classes"

REM Compile all Java files
javac -cp "lib/*" -d target/classes src/main/java/connection/*.java src/main/java/Model/*.java src/main/java/View/*.java src/main/java/Controller/*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [LOI] Khong the compile du an!
    echo Vui long kiem tra lai ma nguon.
    pause
    exit /b 1
)

echo Compile thanh cong!
echo.
echo Dang khoi dong ung dung...
echo.

REM Run the application
java -cp "target/classes;lib/*" View.Main

echo.
echo Ung dung da dong.
pause