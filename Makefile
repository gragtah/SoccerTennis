# Switched over to Ant for no good reason.  No idea if this makefile is still working.

SLICK_LIB := lib/slick/lib
CLASSPATH := .:${SLICK_LIB}/slick.jar:${SLICK_LIB}/lwjgl.jar

.SUFFIXES: .java .class

.java.class:
	javac -cp ${CLASSPATH} $*.java

CLASSES = \
		SoccerTennis.java \
		Player.java \
		Ball.java

default: compile run

compile: $(CLASSES:.java=.class)

run:
	java -Djava.library.path=${SLICK_LIB} -cp ${CLASSPATH} SoccerTennis

clean:
	$(RM) *.class
