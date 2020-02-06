#include <stdio.h>
#include <malloc.h>
#include <memory.h>

#include <openssl/pem.h>
#include <openssl/rsa.h>
#include <openssl/md5.h>

int main(int argc, char** argv)
{
	if(argc == 3) {
		FILE* fsrc = NULL;
		FILE* fdst = NULL;
		errno_t err;
    	MD5_CTX ctx;

    	unsigned char finalDigest[MD5_DIGEST_LENGTH];
    	MD5_Init(&ctx);
		    	unsigned char* fileBuffer = NULL;

		err = fopen_s(&fsrc, argv[1], "rb");	    
		fseek(fsrc, 0, SEEK_END);
		int fileLen = ftell(fsrc);
		fseek(fsrc, 0, SEEK_SET);

		fileBuffer = (unsigned char*)malloc(fileLen);
		fread(fileBuffer, sizeof(unsigned char), fileLen, fsrc);
    	MD5_Update(&ctx, fileBuffer, fileLen);
    	MD5_Final(finalDigest, &ctx);
		
    	for( int i=0; i<MD5_DIGEST_LENGTH; i++) 
        	printf( "%2X ", finalDigest[i] );        	
		printf("\n");
    	

		fclose(fsrc);

		err = fopen_s(&fdst, argv[2], "wb");
					
		RSA* apriv;
		FILE* f;
		
		unsigned char* buf = NULL;
		unsigned char* e_data = NULL;
		unsigned char* last_data = NULL;

		apriv = RSA_new();		

		f = fopen("privKeyFile.pem","r");
		apriv = PEM_read_RSAPrivateKey(f, NULL, NULL, NULL);
		fclose(f);

		buf = (unsigned char *) malloc(sizeof(finalDigest));      
		memcpy(buf, finalDigest, sizeof(finalDigest));
			
		e_data = (unsigned char *) malloc(RSA_size(apriv)); //RSA_size => 1024 bits

		RSA_private_encrypt(sizeof(finalDigest), buf, e_data, apriv, RSA_PKCS1_PADDING);

		fwrite(e_data, RSA_size(apriv), 1, fdst);

		fclose(fdst);
			
		free(e_data);
		free(buf);
		
		RSA_free(apriv);						
    } 
	else {
		printf("\n Usage mode: OpenSSLProj.exe fSrc.txt eSignFsrc.txt");
		return 1;
    }

    return 0;
}