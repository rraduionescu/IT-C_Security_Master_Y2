#include <openssl/md5.h>
#include <iostream>

#define CHUNK_SIZE 128

int main() {

	FILE* file = new FILE;
	errno_t error;
	
	MD5_CTX context;
	MD5_Init(&context);

	error = fopen_s(&file, "toHash.txt", "rb");

	if (error == 0) {
		//get file size
		fseek(file, 0, SEEK_END);
		int fileLength = ftell(file);
		fseek(file, 0, SEEK_SET);

		unsigned char* buffer = new unsigned char[fileLength];
		fread(buffer, fileLength, 1, file);
		unsigned char* tempBuffer = buffer; //save initial text in temp buffer

		while (fileLength > 0) {
			if (fileLength > CHUNK_SIZE) {
				MD5_Update(&context, tempBuffer, CHUNK_SIZE);
			}
			else {

				MD5_Update(&context, tempBuffer, fileLength);
			}

			fileLength -= CHUNK_SIZE;
			tempBuffer += CHUNK_SIZE;
		}

		unsigned char finalDigest[MD5_DIGEST_LENGTH];
		MD5_Final(finalDigest, &context);

		printf("HEX: ");
		for (int i = 0; i < MD5_DIGEST_LENGTH; i++)
			printf("%02X", finalDigest[i]);
		printf("\n");
		
		FILE* fos = NULL;
		fopen_s(&fos, "cipher.txt", "wb");

		for (int i = 0; i < MD5_DIGEST_LENGTH; i++) {
			fprintf(fos, "%2X", finalDigest[i]);
			fprintf(fos, " ");
			printf("%2X", finalDigest[i]);
		}
	}

	return 0;
}

