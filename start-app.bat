@echo off
echo Setting up environment...
set JAVA_HOME=C:\Program Files\Java\jdk-24
set PATH=%JAVA_HOME%\bin;%PATH%

echo Java version:
java -version

echo.
echo Starting Spring Boot Bookstore Application...
echo This will take a moment to start up...
echo.
echo Once started, you can access:
echo - API: http://localhost:3001/api/books
echo - H2 Console: http://localhost:3001/h2-console
echo.
echo Press Ctrl+C to stop the application
echo.

gradlew.bat bootRun

pause 