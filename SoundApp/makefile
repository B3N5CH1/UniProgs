OBJ = main.o screen.o sound.o
APPNAME = soundApp

# This iterates through all elements and links them to the executable
# -lcurl is required to send data to a server
$(APPNAME): $(OBJ)
	gcc -o $(APPNAME) $(OBJ) -lm -lcurl

# Creates from every .c file a corresponding .o file 
%.o : %.c
	gcc -c -o $@ $<

# Removes any unwanted files
clean :
	rm $(OBJ) $(APPNAME) test.wav

# Creates an archive of the source code, makefile and the readme
due : 
	tar -cvf soundApp_e1600531.tar *.c *.h makefile README.txt license.txt
