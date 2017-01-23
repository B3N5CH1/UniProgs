Readme.txt of acoustic sensor project
Author: Benjamin Graf
        grafb6@bfh.ch
Date: Fall 2016

Content
1. System requirements
  1.1 Hardware
  1.2 Software
2. How to build the project
  2.1 Make
  2.2 Clean
  2.3 Due
  2.4 Conditional Compiling
  2.4.1 Different Options
3. How to use the application
4. What it does
5. Known bugs
6. License

1. System Requirements
  1.1 Hardware
    - This software was programmed on a Raspberry Pi Model 3
      Using a USB Sound Card and a microphone
    - If you don't use a RPi 3, you need another kind of Sound Card and an audio input source
    - If you want to process the data on a server, you need an active connection to that server
  1.2 Software
    - alsa-utils 1.0.25-4
      The version 1.0.28-1 has some bugs, which prevents from recording properly
    - You also need to have the rights, which allow you to use alsamixer, alsa-utils respectively
   - libcurl14-nss-dev (7.38.0-4)
      Other versions might work, but it was only testet with the one mentioned above
      If you want to use the COMM mode (see 2.4.1)

2. How to build the project
  2.1 Make
    - Use the console to 'make' the executable, no further command or argument required
  2.2 Clean
    - To remove any .o files or the executable, use 'make clean'
  2.3 Due
    - To create an archive of the source code, the makefile and the readme.txt, use 'make due'
  2.4 Change conditional compiling settings
    - If you want to change the conditional compiling, open sound.h in your preferred text editor
    - Comment / Uncomment the desired settings
    - Build the project
  2.4.1 Debug Settings
    - DEBUG
      DEBUG mode displays the value in dB insted of a graphical bar
    - COMM
      Uses curl to connect to the server, specified in sound.c -> sendData(),
      To run the script which processes the given data.
	  
3. How to use the application
  - Just run the soundApp executable. Usually you do this by the command './soundApp'
  - You exit the program by pressing Ctrl-C
  - You need to have the proper user rights to record a sound with the alsautils 
  
4. What it does
  - It records a sound which is then graphically displayed, depending on the volume.
  - It also can communicate with a server and the given .php file, so it creates
    a .txt file, where it logs the volume in dB.

5. Known bugs
  - Currently there is only one known bug
  - Sometimes when you terminate the program the clearScreen() doesn't work properly
    So you have to use 'clear'

6. License
  - This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

  - This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

  - For more information see the file 'license.txt' or <http://www.gnu.org/licenses/>.
