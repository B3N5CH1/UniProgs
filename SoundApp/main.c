#include "screen.h"
#include "sound.h"
#include <stdio.h>
#include <signal.h>
#include <sys/wait.h>

int main(int argc, char *argv[]){
	WAVHEADER myhdr;
	FILE *fp;	//file header
	short int samples[FS];	// for 1 second of samples
	int ret;

	// Endless loop, until Ctrl-C is pressed or the process killed
	for (;;) {
		// -r is the sample rate
		// -c is the amount of channels (mono/stereo...)
		// -d is the duration (1 second is recommended)
		// test.wav is the output file
		ret = system("arecord -q -r16000 -f S16_LE -c1 -d1 test.wav");
		
		// checks if Ctrl-C is pressed, if yes, the program is terminated
		if(WIFSIGNALED(ret) && WTERMSIG(ret)==SIGINT || WTERMSIG(ret)==SIGQUIT) break;
		
		// tries to open the previously created file
		// reads from the file and displays the data accordingly
		fp = fopen("test.wav", "rb");
		if(fp!=NULL){	//if the file is successfully opened
			clearScreen();	//erase and clear an empty screen
			fread(&myhdr, sizeof(myhdr), 1, fp);
			fread(&samples, sizeof(short int), FS, fp);
			displayWAVHdr(myhdr);
			displayWAVdata(samples);
			fclose(fp);
		}
	}

	// Clears the screen
	clearScreen();
	printf("\n");

#ifdef DEBUG	// conditional compiling
	createTestTone(1000);
#endif
}

