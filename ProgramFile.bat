@echo off
mkdir build
javac Main.java -d build
java -cp build Main
del build
rem Choose Y to clear the directory
rmdir build