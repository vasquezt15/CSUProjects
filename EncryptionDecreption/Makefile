C_SRCS = Starter.c
EXEC = Starter

CFLAGS = -Wall -Wextra -Werror -Wfatal-errors

TAR_NAME = VASQUEZ_TOMAS_HW1.tar
build: $(EXEC)

$(target): $(C_SRCS)
	cc $^  -o $@

# This phony target (via “make clean”) is used to remove unneeded files.
package:
	tar -zcvf $(TAR_NAME) $(C_SRCS)  Makefile README.txt

clean:
	-rm -f *.o *.gch $(target) $(EXEC)
