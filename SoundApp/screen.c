#include <stdio.h>
#include "screen.h"

/*
 * This function displays a bar (defined in screen.h with the unicode).
 * The color used for the bar ar defined in screen.h and could be changed.
 * The color depends on the strength of the input.
 *
 * @param x the position on the screen (x-axis)
 * @param y the amplitude of the recorded sound (in dB)
*/
void displayBar(int x, int y) {
	int i;
	for (i = 0; i < y/3; i++) {
		gotoxy(30 - i, x);
		if (y > 60 && i > 60/3) {
			setFgcolor(RED);
		} else if (y > 30 && i > 30/3) {
			setFgcolor(YELLOW);
		} else {
			setFgcolor(GREEN);
		}
		printf("%s", BAR);
	}
}

/*
 * This function resets any color settings to be the default ones.
*/
void resetColors(void){
	printf("\033[0m");
	fflush(stdout);
}

/*
 * Displays the given input with the desired color settings
 * at the desired location.
 *
 * @param *s the message to be displayed
 * @param f the foreground color, as defined in screen.h
 * @param b the background color, as defined in screen.h
 * @param row the row, where it should be printed
 * @param col the column, where it should be printed
*/
void displayXY(char *s, int f, int b, int row, int col){
	gotoxy(row, col);
	setFgcolor(f);
	setBgcolor(b);
	printf("%s", s);
	resetColors();
}

/*
 * Sets the cursor to the given position on the screen
 *
 * @param row the row, where the cursor should be placed
 * @param col the column, where the cursor should be placed
*/
void gotoxy(int row, int col){
	printf("\033[%d;%dH", row, col);
	fflush(stdout);
}

/*
 * Clears the screen. Same effect as in a shell the 'clear' command
*/
void clearScreen(void){
	printf("\033[2J");
	fflush(stdout);
}

/*
 * Sets the foreground color to the desired value
 *
 * @param a the value of the color. Defined in screen.h
*/
void setFgcolor(int a){
	printf("\033[1;%dm", a);	//escape sequence to secolor
	fflush(stdout); //force the attribute flushed to the terminal
}

/*
 * Sets the background color to the desired value
 *
 * @param a the value of the color. Defined in screen.h
*/
void setBgcolor(int a){
	printf("\033[1;%dm", a);
	fflush(stdout);
}
