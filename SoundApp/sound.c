#include <stdio.h>
#include <math.h>
#include "sound.h"
#include "screen.h"

#define PI 3.1415926

/*
 * If in COMM mode this function send the given values (in dB) to the server,
 * where they are processed with the php script.
 *
 * @param *dbs the pointer to the dB values which should be sent to the server.
*/
#ifdef COMM
#include <curl/curl.h>
#include <string.h>
void sendData(double *dbs) {
	CURL *curl;
	CURLcode res;
	char postdata[100], tmp[100];
	int i;

	// Initiation of the curl
	curl_global_init(CURL_GLOBAL_ALL);
	curl = curl_easy_init();
	
	// If there was no proplem with the initiation (which is the normal case),
	// the postdata is filled with the dB values and then sent to the server.
	if (curl) {
		sprintf(postdata, "%s", "dBs=");
		for (i = 0; i < 8 ; i++) {
			sprintf(tmp, "%2.f;", dbs[i]);
			strcat(postdata, tmp);
		}
		
		// Change URL depending on the location of the file/server
		curl_easy_setopt(curl, CURLOPT_URL, "http://www.cc.puv.fi/~e1600531/php/sound.php");
		curl_easy_setopt(curl, CURLOPT_POSTFIELDS, postdata);
		res = curl_easy_perform(curl);
		if (res != CURLE_OK) printf("Cannot send data");
		curl_easy_cleanup(curl);
	}
	
	curl_global_cleanup();
}
#endif

/*
 * This function displays the samples given.
 * It calculates the rms value and then displays the dB value
 * Either as a number (DEBUG mode) or as a vertical bar (not DEBUG mode)
 * Also if in COMM mode, it sends every 1/8 of a second (as a dB value) to the server.
 *
 * @param *s the samples, which should be displayed
*/
void displayWAVdata(short int *s) {
	int i, j, k;	// loop variables
	double rms, sum, sum_short;
	double v[8];
	k = 0;
	sum_short = 0;	// sum of samples for 1/8 seconds

	for (i = 0; i < 80; i++) {	// outer loop 80x (since screen width)
		for (j = 0, sum = 0.0; j < FS/80; j++) {	// inner loop, 200x if FS=16k
			sum += (*s)*(*s);	// square of current sample
			s++;		// pointer incr
		}
		rms = sqrt(sum/(FS/80));	// calculate RMS value for 200 samples
		sum_short += sum;	// for 2k samples
		
		// This is actually only used for COMM mode
		// for every 1/8 of a second, the rms value is calculated
		// and stored in v[]
		if (i%10 == 9) {
			v[k] = 20*log10(sqrt(sum_short/(FS/8)));
			k++;
			sum_short = 0;
		}

	// if in DEBUG mode, the dB value is printed, else a vertical bar is displayed 
#ifdef DEBUG
		printf("rms(dB) %d = %.3f\n", i, 20*log10(rms));
#else
		displayBar(i+1, (int)(20*log10(rms)));
#endif
	}

	// if in COMM mode, the data is sent to the server,
	// calling a php script to process the sent data
#ifdef COMM
	sendData(v);
#endif

}

/*
 * This function prints the id of the chunk as defined in the wav header struct.
 *
 * @param id[] the id which should be printed
*/
void printID(char id[]){
	int i;
	for(i=0; i<4; i++) printf("%c", id[i]);
	puts("");
}

/*
 * This function displays information about the .wav file.
 * If in DEBUG mode, it displays every item in the struct as a list.
 * If not, it displays the most important ones (duration, channels etc.) at the top of the screen.
 *
 * @param h The header of the current .wav file, as defined in the sound.h
*/
void displayWAVHdr(WAVHEADER h){
	double duration;
	duration = (double)h.SubChunk2Size / h.ByteRate;
	char str[25];
#ifdef DEBUG
	printf("1.Chunk ID: "); printID(h.ChunkID);
	printf("2.Chunk Size: %d\n", h.ChunkSize);
	printf("3.Format: "); printID(h.Format);
	printf("4.Subhunk ID: "); printID(h.SubChunk1ID);
	printf("5.Subchunk1 Size: %d\n", h.SubChunk1Size);
	printf("6.Audio Format: %d\n", h.AudioFormat);
	printf("7.Num of Channels: %d\n", h.NumChannels);
	printf("8.Sample Rate: %d\n", h.SampleRate);
	printf("9.Byte rate: %d\n", h.ByteRate);
	printf("10.Block Align: %d\n", h.BlockAlign);
	printf("11.Bits per Sample: %d\n", h.BitsPerSample);
	printf("12.Subchunk2 ID: "); printID(h.SubChunk2ID);
	printf("13.SubChunk2 size: %d\n", h.SubChunk2Size);
	printf("Duration: %.2f seconds\n", duration);
#else	// following code is for final application
		// we are going to display No.Ch, SampleRate, bpSample & duration
		// at the top of the screen
	sprintf(str, "No. of channels:   %d", h.NumChannels);
	displayXY(str, RED, bg(BLUE), 1, 1);
	sprintf(str, "Sample Rate:   %d", h.SampleRate);
	displayXY(str, GREEN, bg(WHITE), 1, 21);
	sprintf(str, "Bits per second:  %2d", h.BitsPerSample);
	displayXY(str, YELLOW, bg(RED), 1, 41);
	sprintf(str, "Duration:       %.3fs", duration);
	displayXY(str, MAGENTA, bg(YELLOW), 1, 61);
#endif

}

/*
 * This function creates (or if one called 'TestTone.wav' already exists, opens) a .wav file
 * With the frequency as a parameter. 
 * The file is of 5 seconds duration.
 * Could be changed, if necessary, at the beginning (noted)
 *
 * @param freq the frequency of the testtone
*/
void createTestTone(int freq) {
	FILE*fp;			// file handler
	short int sample;	// for each sample 16 bit signed
	int i, duration = 5;// loop counter, sample duration
	WAVHEADER h;

	fp = fopen("TestTone.wav", "wb");	// trying to open (create) a file

	// if the program was able to open/create the file,
	// it fills the header with the following data
	if (fp != NULL) {
		fillID(h.ChunkID, "RIFF");
		fillID(h.Format, "WAVE");
		fillID(h.SubChunk1ID, "fmt ");
		fillID(h.SubChunk2ID, "data");
		h.SubChunk1Size = 16; h.AudioFormat = 1; // PCM encoding
		h.NumChannels = 1; h.SampleRate = 16000; // Mono 16000 Fs
		h.ByteRate = 16000 * 2; h.BlockAlign = 2;
		h.BitsPerSample = 16; h.SubChunk2Size = 16000 * 5 * 2;
		h.ChunkSize = h.SubChunk2Size + 36;

		fwrite(&h, sizeof(h), 1, fp);	// write header to wav file

		// for every sample it writes the content to the file
		for (i = 0; i < duration*16000; i++) {
			sample = sin(2 * PI * freq * i / 16000) * 32768;
			fwrite(&sample, sizeof(sample), 1, fp);
		}

		fclose(fp);

		printf("Test tone created\n");

	}

}

/*
 * This function fills a certain elemnt in the wav header struct with the desired value.
 *
 * @param *id the pointer to the struct element of the wav header
 * @param *s the pointer to the content, which should be written to the wav header
*/
void fillID(char *id, char *s) {
	int i;
	for (i = 0; i < 4; i++) *id++ = *s++;
}
