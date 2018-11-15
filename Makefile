C=javac
E=java

all:
	cd src/ && $(C) org/Main.java

exec:
	cd src && $(E) org.Main

execute: exec