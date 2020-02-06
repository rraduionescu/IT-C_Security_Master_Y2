//#include <iostream>
//#include <openssl/aes.h>
//#include <openssl/sha.h>
//
//int main()
//{
//	file* messagefile;
//	errno_t messagefileerror = fopen_s(&messagefile, "message.txt", "r");
//	if (!messagefileerror)
//	{
//		fseek(messagefile, 0, seek_end);
//		printf("message length    : %d bytes\n", ftell(messagefile));
//		int messagelength = ftell(messagefile);
//		unsigned char* message = (unsigned char*)malloc(messagelength + 1);
//		fseek(messagefile, 0, seek_set);
//		fread(message, messagelength, 1, messagefile);
//		message[messagelength] = '\0';
//		printf("message           : %s\n\n", message);
//
//		sha_ctx ctx;
//		sha1_init(&ctx);
//		sha1_update(&ctx, message, messagelength);
//		unsigned char* sha1hash = (unsigned char*)malloc(sha_digest_length);
//		sha1_final(sha1hash, &ctx);
//		printf("sha-1 hash        : ");
//		for (int i = 0; i < sha_digest_length; i++)
//		{
//			printf("%02x ", sha1hash[i]);
//		}
//		printf("\n");
//
//		int outputlength;
//		if (messagelength % aes_block_size == 0)
//		{
//			outputlength = messagelength;
//		}
//		else
//		{
//			outputlength = messagelength + (aes_block_size - messagelength % aes_block_size);
//		}
//		unsigned char key[aes_block_size] = {	(unsigned char)'p', (unsigned char)'a', (unsigned char)'r', (unsigned char)'o', 
//												(unsigned char)'l', (unsigned char)'a', (unsigned char)'p', (unsigned char)'u', 
//												(unsigned char)'t', (unsigned char)'e', (unsigned char)'r', (unsigned char)'n', 
//												(unsigned char)'i', (unsigned char)'c', (unsigned char)'a', (unsigned char)'#'
//											};
//		unsigned char* output = (unsigned char*)malloc(outputlength);
//		aes_key aeskey;
//		aes_set_encrypt_key(key, 128, &aeskey);
//		for (int i = 0; i < (outputlength / 16); i++)
//		{
//			aes_encrypt(&(message[i*16]), &(output[i*16]), &aeskey);
//		}
//		aes_encrypt(message, output, &aeskey);
//		printf("encrypted message : ");
//		for (int i = 0; i < outputlength; i++)
//		{
//			printf("%02x ", output[i]);
//		}
//		printf("\n");
//	}
//	else
//	{
//		printf("%s", "file opening error!");
//	}
//
//	return 0;
//}