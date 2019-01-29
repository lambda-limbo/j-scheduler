JAVAC = javac
JAVA = java
JAR = jar

FLAGS =

SRC = org/Main.java org/scheduler/view/Greetings.java \
	  org/scheduler/view/MainView.java                \
      org/scheduler/model/Processor.java              \
	  org/scheduler/model/Process.java                \
      org/scheduler/model/Scheduler.java              \
	  org/scheduler/model/ProcessComparator.java      \
	  org/scheduler/controller/ProcessController.java \
	  org/scheduler/controller/TableController.java

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
