
#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <openssl/aes.h>

int main(int argc, char** argv)
{
	if(argc == 5) {
		FILE* fSrc = NULL, *fDst = NULL;
		errno_t err;
		char opt[3];
		char mode[7];
		strcpy(opt, argv[1]);
		strcpy(mode, argv[2]);

		AES_KEY akey;
		unsigned char* inBuf = NULL;
		unsigned char* outBuf;
		unsigned char ivec[16];
		unsigned char userSymmetricKey[16] = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07};

		if(strcmp(opt, "-e") == 0) {
			fopen_s(&fSrc, argv[3], "rb");
			fopen_s(&fDst, argv[4], "wb");
			fseek(fSrc, 0, SEEK_END);
			long int inLen = ftell(fSrc);
			fseek(fSrc, 0, SEEK_SET);
			long int outLen = 0;
			if((inLen % 16) == 0)
				outLen = inLen;
			else
				outLen = ((inLen/16)*16) + 16;
			
			inBuf = (unsigned char*)malloc(outLen);
			outBuf = (unsigned char*)malloc(outLen);
			memset(inBuf, 0x00, outLen);
			fread(inBuf, inLen, 1, fSrc);

			AES_set_encrypt_key(userSymmetricKey, 128, &akey);
			if(strcmp(mode, "-ecb") == 0) {
				for(int i = 0; i < (outLen/16); i++)
					AES_encrypt(&(inBuf[i*16]), &(outBuf[i*16]), &akey);
			} else {
				memset(&ivec, 0x01, sizeof(ivec));
				AES_cbc_encrypt(inBuf, outBuf, outLen, &akey, ivec, AES_ENCRYPT);
			}
			
			fwrite(&inLen, sizeof(inLen), 1, fDst);
			fwrite(outBuf, outLen, 1, fDst);
			free(outBuf);
			free(inBuf);
			fclose(fDst);
			fclose(fSrc);
		} else {
			fopen_s(&fSrc, argv[3], "rb");
			fopen_s(&fDst, argv[4], "wb");
			fseek(fSrc, 0, SEEK_END);
			long int inLen = ftell(fSrc) - 4;
			fseek(fSrc, 0, SEEK_SET);
			long int outLen = 0;
			fread(&outLen, sizeof(outLen), 1, fSrc);
			
			inBuf = (unsigned char*)malloc(inLen);
			outBuf = (unsigned char*)malloc(inLen);
			memset(inBuf, 0x00, inLen);
			fread(inBuf, inLen, 1, fSrc);

			AES_set_decrypt_key(userSymmetricKey, 128, &akey);
			if(strcmp(mode, "-ecb") == 0) {
				for(int i = 0; i < (inLen/16); i++)
					AES_decrypt(&(inBuf[i*16]), &(outBuf[i*16]), &akey);
			} else {
				memset(&ivec, 0x01, sizeof(ivec));
				AES_cbc_encrypt(inBuf, outBuf, inLen, &akey, ivec, AES_DECRYPT);
			}
			
			fwrite(outBuf, outLen, 1, fDst);
			free(outBuf);
			free(inBuf);
			fclose(fDst);
			fclose(fSrc);
		}
	} else {
		printf("\n Usage Mode: OpenSSLProj.exe -e -cbc fSrc.txt fDst.txt");
		printf("\n Usage Mode: OpenSSLProj.exe -d -ecb fSrc.txt fDst.txt");
		return 1;
	}
	printf("\n Process done.");
	return 0;
}
