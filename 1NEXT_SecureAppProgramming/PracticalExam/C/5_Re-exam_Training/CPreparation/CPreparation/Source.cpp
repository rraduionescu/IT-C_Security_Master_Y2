#include <iostream>
#include <openssl/md5.h>
#include <openssl/sha.h>

int main()
{
	// 1. Compute the MD5 and SHA-256 hashes of the content of "Message.txt" text file

	FILE* inputFile, * outputMD5File, * outputSHAFile;
	errno_t inputError = fopen_s(&inputFile, "Message.txt", "r"), outputMD5Error = fopen_s(&outputMD5File, "MD5.hash", "wb"), outputSHAError = fopen_s(&outputSHAFile, "SHA256.hash", "wb");
	if (!inputError && !outputMD5Error && !outputSHAError)
	{
		// Alocare spatiu hashuri
		unsigned char* MD5Hash = (unsigned char*)malloc(MD5_DIGEST_LENGTH);
		unsigned char* SHAHash = (unsigned char*)malloc(SHA256_DIGEST_LENGTH);

		// Citirea si afisarea fisierului
		fseek(inputFile, 0, SEEK_END);
		int length = ftell(inputFile);
		char* input = (char*)malloc(length + 1);
		fseek(inputFile, 0, SEEK_SET);
		fread(input, length, 1, inputFile);
		input[length] = '\0';
		printf("\nLength: %d String: %s\n\n", length, input);
		
		// Calcularea si afisarea hashului MD5
		MD5_CTX MD5_ctx;
		MD5_Init(&MD5_ctx);
		MD5_Update(&MD5_ctx, input, length);
		MD5_Final(MD5Hash, &MD5_ctx);
		printf("MD5 Hash: ");
		for (int i = 0; i < MD5_DIGEST_LENGTH; i++)
		{
			printf("%02X ", MD5Hash[i]);
		}
		printf("\n");

		// Calcularea si afisarea hashului SHA-256
		SHA256_CTX SHA_ctx;
		SHA256_Init(&SHA_ctx);
		SHA256_Update(&SHA_ctx, input, length);
		SHA256_Final(SHAHash, &SHA_ctx);
		printf("SHA Hash: ");
		for (int i = 0; i < SHA256_DIGEST_LENGTH; i++)
		{
			printf("%02X ", SHAHash[i]);
		}
		printf("\n");

		// Scrierea hashurilor in fisiere
		fwrite(MD5Hash, MD5_DIGEST_LENGTH, 1, outputMD5File);
		fwrite(SHAHash, SHA256_DIGEST_LENGTH, 1, outputSHAFile);
	}

	return 0;
}