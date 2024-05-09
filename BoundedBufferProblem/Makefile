COMPLIER= javac
JRE = java
FILES = Coordinator.java Buffer.java Producer.java Consumer.java
EXE = Coordinator
TAR_NAME = HW_5_Solution_ExtraCredit.tar
all:
	$(COMPLIER) $(FILES)
run:
	$(JRE) $(EXE)
package:
	tar -zcvf $(TAR_NAME) $(FILES) Makefile HW5README.txt
clean:
	rm *.class