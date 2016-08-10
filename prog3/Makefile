
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
		OPT.java \
		LRU.java \
        FIFO.java \
        Algorithm.java \
        MemoryController.java \
        prog3.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class
