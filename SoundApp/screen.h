// Used for color definition for text and background
// Numbers based on shell color values
enum COLORS{BLACK=30, RED, GREEN, YELLOW, BLUE, MAGENTA, CYAN, WHITE};
#define bg(c) (c+10)	// macro
#define BAR "\u2590"	// unicode symbol for a bar

// function prototype to display at a certain position
// with certain color settings
void displayXY(char *s, int, int, int, int);

// function prototype to move the cursor to the desired postition
void gotoxy(int, int);

// function prototype to clear the screen
void clearScreen(void);

// function prototype to set the foreground color
void setFgcolor(int);

// function prototype to set the background color
void setBgcolor (int);

// function prototype to display a vertical bar
void displayBar(int x, int y);
