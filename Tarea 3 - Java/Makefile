JAVAC = javac
JAVA = java
SRC = $(wildcard *.java entorno/*.java objetos/*.java player/*.java)
CLASSES = $(SRC:.java=.class)
MAIN = Main

all: clases

clases:
	$(JAVAC) $(SRC)

run: all
	$(JAVA) $(MAIN)

clean:
	rm -f *.class entorno/*.class objetos/*.class player/*.class