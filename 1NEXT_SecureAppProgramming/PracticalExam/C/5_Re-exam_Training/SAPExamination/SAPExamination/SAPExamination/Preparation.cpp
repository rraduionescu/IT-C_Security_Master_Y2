#include <iostream>
#include <openssl/sha.h>
#include <openssl/aes.h>

int main()
{
	FILE* AESEncryptedKeyFIle;
	FILE* messageFile;

	errno_t AESEncryptedKeyFIleError = fopen_s(&AESEncryptedKeyFIle, "ClientISM.key", "rb");
	errno_t messageFileError = fopen_s(&messageFile, "Message.txt", "rb");

	if (!AESEncryptedKeyFIleError && !messageFileError)
	{
		printf("\n");

		// Hash input file allocation and reading
		fseek(AESEncryptedKeyFIle, 0, SEEK_END);
		int AESEncryptedKeyFIleLength = ftell(AESEncryptedKeyFIle);
		unsigned char* AESEncryptedKeyFIleContent = (unsigned char*)malloc(AESEncryptedKeyFIleLength);
		fseek(AESEncryptedKeyFIle, 0, SEEK_SET);

		fread(AESEncryptedKeyFIleContent, AESEncryptedKeyFIleLength, 1, AESEncryptedKeyFIle);

		// SHA-1 hash computation
		SHA_CTX ctx;
		SHA1_Init(&ctx);
		SHA1_Update(&ctx, AESEncryptedKeyFIleContent, AESEncryptedKeyFIleLength);
		unsigned char* SHA1Hash = (unsigned char*)malloc(SHA_DIGEST_LENGTH);
		SHA1_Final(SHA1Hash, &ctx);

		// SHA-1 hash display
		printf("SHA-1 hash          : ");
		for (int i = 0; i < SHA_DIGEST_LENGTH; i++)
		{
			printf("%02X ", SHA1Hash[i]);
		}

		// Memory deallocation
		free(AESEncryptedKeyFIleContent);
		free(SHA1Hash);

		// Encryption input file allocation and reading
		fseek(messageFile, 0, SEEK_END);
		int messageFileLength = ftell(messageFile);
		unsigned char* messageFileContent = (unsigned char*)malloc(messageFileLength);
		fseek(messageFile, 0, SEEK_SET);

		fread(messageFileContent, messageFileLength, 1, messageFile);

		// AES in ECB mode encryption
		const unsigned char key[AES_BLOCK_SIZE] = {'p', 'a', 'r', 'o', 'l', 'a', 'p', 'u', 't', 'e', 'r', 'n', 'i', 'c', 'a', '#'};
		AES_KEY AESKey;
		AES_set_encrypt_key(key, AES_BLOCK_SIZE * 8, &AESKey);
		int outputLength;
		outputLength = messageFileLength + (AES_BLOCK_SIZE - messageFileLength % AES_BLOCK_SIZE);
		unsigned char* messageEncrypted = (unsigned char*)malloc(outputLength);
		for (int i = 0; i < (outputLength / AES_BLOCK_SIZE); i++)
		{
			AES_encrypt(&(messageFileContent[i * AES_BLOCK_SIZE]), &messageEncrypted[i * AES_BLOCK_SIZE], &AESKey);
		}

		// Ciphertext display
		printf("\nMessage ciphertext  : ");
		for (int i = 0; i < outputLength; i++)
		{
			if (i > 0 && i % AES_BLOCK_SIZE == 0)
			{
				printf("\n                      ");
			}
			printf("%02X ", messageEncrypted[i]);
		}

		// Memory deallocation
		free(messageFileContent);
		free(messageEncrypted);

		// AES decryption in CBC mode with no padding
		FILE* data;
		errno_t dataError = fopen_s(&data, "EncryptedMessage.cipher", "rb");
		fseek(data, 0, SEEK_END);
		int dataLength = ftell(data);
		unsigned char* dataContent = (unsigned char*)malloc(dataLength);
		unsigned char* outputContent = (unsigned char*)malloc(dataLength);
		fseek(data, 0, SEEK_END);
		fread(dataContent, dataLength, 1, data);
		AES_KEY cbcKey;
		unsigned char cbcKeyBytes[AES_BLOCK_SIZE] = {	0x2a, 0x4d, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20,
														0x49, 0x53, 0x4D, 0x20, 0x32, 0x30, 0x31, 0x37 };
		unsigned char IV[AES_BLOCK_SIZE] = {			0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
														0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01 };
		AES_set_decrypt_key(cbcKeyBytes, AES_BLOCK_SIZE * 8, &cbcKey);
		AES_cbc_encrypt(dataContent, outputContent, dataLength, &cbcKey, IV, AES_DECRYPT);
		for (int i = 0; i < dataLength; i++)
		{
			printf("%s", outputContent);
		}

		printf("\n");
	}
	else
	{
		printf("Error opening files!");
	}

	return 0;
}