#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include <openssl/aes.h>
#include <openssl/sha.h>

int main(int argc, char** argv)
{
	printf("\nSecuring user passwords...\n");
	printf("Computing AES-CBC-256(SHA-256(<user_password>))\n\n");

	FILE *sourceFile = NULL, *encryptedFile = NULL, *hexFile;

	SHA256_CTX ctx;
	AES_KEY aesKey;

	errno_t inputFileError, outputFileError, hexFileError;
	unsigned char finalDigest[SHA256_DIGEST_LENGTH];
	unsigned char* outputBuffer = NULL;
	unsigned char IV[16];
	unsigned char aesKeyBytes[32] = {	
										0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
										0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F,
										0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
										0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F
									};

	inputFileError = fopen_s(&sourceFile, "wordlist_top_500PswMangled.txt", "rb");
	outputFileError = fopen_s(&encryptedFile, "passwords.enc", "wb");
	hexFileError = fopen_s(&hexFile, "passwords.txt", "w");

	char line[128];

	if (inputFileError == 0 && outputFileError == 0 && hexFileError == 0 && sourceFile && encryptedFile && hexFile)
	{
		while (fgets(line, sizeof line, sourceFile) != NULL)
		{
			SHA256_Init(&ctx);
			SHA256_Update(&ctx, line, (strlen(line) - 2));
			SHA256_Final(finalDigest, &ctx);

			int inputLength = SHA256_DIGEST_LENGTH;
			int outputLength = inputLength;

			outputBuffer = (unsigned char*)malloc(outputLength);

			AES_set_encrypt_key(aesKeyBytes, 256, &aesKey);

			memset(&IV, 0x01, sizeof(IV));
			AES_cbc_encrypt(finalDigest, outputBuffer, outputLength, &aesKey, IV, AES_ENCRYPT);

			if (outputBuffer)
			{
				fwrite(outputBuffer, outputLength, 1, encryptedFile);
				for (int i = 0; i < outputLength; i++)
				{
					fprintf(hexFile, "%2X", outputBuffer[i]);
					fprintf(hexFile, " ");
				}
				fprintf(hexFile, "\n");
			}
		}
	}
	
	if (sourceFile && encryptedFile && hexFile)
	{
		fclose(encryptedFile);
		fclose(sourceFile);
		fclose(hexFile);
	}

	printf("Done encrypting password SHA256 hashes!\n\n");
	printf("Saved binary password data:      passwords.enc\n");
	printf("Saved hex text representation:   passwords.txt\n");

	return 0;
}