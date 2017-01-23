//#define DEBUG 	// for conditional compiling
#define COMM		// also conditional compiling
#define FS 16000	// SampleRate

// defines, how the header of a WAV file
typedef struct{
	char ChunkID[4];	//chunk ID, text
	int ChunkSize;		//chunk size, 4bytes
	char Format[4];		//"WAVE"
	char SubChunk1ID[4];	//"fmt"
	int SubChunk1Size;	//4 bytes
	short int AudioFormat;	//2 bytes
	short int NumChannels;	//2 bytes
	int SampleRate;		//4 bytes
	int ByteRate;		//4 bytes
	short int BlockAlign;	//2 bytes
	short int BitsPerSample;	//2 bytes
	char SubChunk2ID[4];	//"data
	int SubChunk2Size;	//4 bytes
}WAVHEADER;

// function prototype to fill a certain value of the WAVHEADER struct with data
void fillID(char *, char *);

// function prototype used for the communication to the server
void sendDtata(double []);
