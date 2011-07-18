SLICK_LIB := lib/slick/lib
CLASSPATH := .:${SLICK_LIB}/slick.jar:${SLICK_LIB}/lwjgl.jar
SoccerTennis:
	javac -cp ${CLASSPATH} SoccerTennis.java
	java -Djava.library.path=${SLICK_LIB} -cp ${CLASSPATH} SoccerTennis
