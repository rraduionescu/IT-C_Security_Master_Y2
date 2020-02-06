#include <stdio.h>
#include <malloc.h>
#include <openssl/sha.h>

#define ADD_STRING_LENGTH 20
#define MESSAGE_CHUNK 160

int main(int argc, char**argv)
{
    if(argc == 2) {

		FILE* f = NULL;
		errno_t err;
		SHA_CTX ctx;

		unsigned char finalDigest[SHA_DIGEST_LENGTH];
		SHA1_Init(&ctx);
	
		unsigned char* fileBuffer = NULL;

		err = fopen_s(&f, argv[1], "rb");
		if(err == 0) {	    
			fseek(f, 0, SEEK_END);
			int fileLen = ftell(f);
			fseek(f, 0, SEEK_SET);

			fileBuffer = (unsigned char*)malloc(fileLen);
			fread(fileBuffer, fileLen, 1, f);
			unsigned char* tmpBuffer = fileBuffer;

			while (fileLen > 0) {
				if (fileLen > MESSAGE_CHUNK) {
					SHA1_Update(&ctx, tmpBuffer, MESSAGE_CHUNK);																			
				} 
				else {
					SHA1_Update(&ctx, tmpBuffer, fileLen);
				}
				fileLen -= MESSAGE_CHUNK;
				tmpBuffer += MESSAGE_CHUNK;
			}

    		//MD5_Update(&ctx, fileBuffer, fileLen);
    	
			SHA1_Final(finalDigest, &ctx);			

			int count = 0;
			printf("\nSHA1 = ");
    		for( int i = 0; i < SHA_DIGEST_LENGTH; i++) {
        		printf( "%2X", finalDigest[i] );        		
            		printf( " " );
    		}
			
			unsigned char addChars[ADD_STRING_LENGTH];
			for (int i = 0; i < ADD_STRING_LENGTH; i++)
				addChars[i] = i + 15;
			printf("\n%s\n", addChars);
			SHA1_Transform(&ctx, addChars);										
			SHA1_Final(finalDigest, &ctx);

			count = 0;
			printf("\nSHA1 = ");
    		for( int i = 0; i < SHA_DIGEST_LENGTH; i++) {
        		printf( "%2X", finalDigest[i] );        		
            		printf( " " );
    		}

			printf("\n");
			
			unsigned char *result = 0;
			result = SHA1(addChars, ADD_STRING_LENGTH, finalDigest);		
		
			count = 0;
			printf("\nSHA1 = ");
    		for( int i=0; i < SHA_DIGEST_LENGTH; i++) {
        		printf( "%2X", finalDigest[i] );        		
            		printf( " " );
    		}

			count = 0;
			printf("\nResult is: ");
    		for( int i = 0; i < SHA_DIGEST_LENGTH; i++) {
        		printf( "%2X", result[i] );        		
            		printf( " " );
    		}
			printf("\n");

			fclose(f);
		}

    } 
	else {
		printf("\n Usage Mode: SHA1.exe fSrc.txt");
		return 1;
    }

    return 0;
}
