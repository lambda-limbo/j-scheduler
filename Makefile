JAVAC = javac
JAVA = java
JAR = jar

FLAGS =

SRC = org/Main.java org/gui/Greetings.java org/gui/MainView.java \
      org/scheduler/Processor.java org/scheduler/Process.java  \
      org/scheduler/Scheduler.java

OBJ = `find .. -name "*.class" | xargs echo`

MAIN_CLASS = org/Main.java

EXEC_NAME = jscheduler.jar

all:
	cd src/ && $(JAVAC) $(FLAGS) $(SRC)

exec:
	cd src && $(JAVA) org.Main

execute: exec

bin: all
	mkdir -p bin && cd bin
	$(JAR) c $(EXEC_NAME) $(OBJ)

clean:
	find . -name "*.class" -print0 | xargs -0 rm -rf