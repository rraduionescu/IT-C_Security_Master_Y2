//#include <stdio.h>
//#include <malloc.h>
//#include <memory.h>
//#include <openssl/pem.h>
//#include <openssl/rsa.h>
//#include <openssl/md5.h>
//
//int main(int argc, char** argv)
//{
//	if(argc == 3) {
//		FILE* fsrc = NULL;
//		FILE* fsig = NULL;
//		errno_t err;
//    	MD5_CTX ctx;
//
//    	unsigned char finalDigest[MD5_DIGEST_LENGTH];
//    	MD5_Init(&ctx);
//
//    	unsigned char* fileBuffer = NULL;
//
//		err = fopen_s(&fsrc, argv[1], "rb");	    
//		fseek(fsrc, 0, SEEK_END);
//		int fileLen = ftell(fsrc);
//		fseek(fsrc, 0, SEEK_SET);
//
//		// Get the MD from the clear input file (clear message)
//		fileBuffer = (unsigned char*)malloc(fileLen);
//		fread(fileBuffer,sizeof(unsigned char), fileLen, fsrc);
//    	MD5_Update(&ctx, fileBuffer, fileLen);
//    	MD5_Final(finalDigest, &ctx);
//		
//    	for( int i=0; i<MD5_DIGEST_LENGTH; i++) 
//        	printf( "%2X ", finalDigest[i] );   
//		printf("\n");
//
//		fclose(fsrc);
//
//		err = fopen_s(&fsig, argv[2], "rb"); // the eSign file is opened (eSign means encrypted MD5 received)
//			
//		RSA* apub;
//		FILE* f;
//		int ret;
//		unsigned char* buf = NULL;
//		unsigned char* last_data = NULL;
//
//		apub = RSA_new();
//	
//		f = fopen("pubKeyFile.pem","r");
//		apub = PEM_read_RSAPublicKey(f, NULL, NULL, NULL);
//		fclose(f);
//
//		buf = (unsigned char *) malloc(RSA_size(apub));
//
//		fread(buf, RSA_size(apub), 1, fsig);
//			
//		last_data = (unsigned char *) malloc(16);
//
//		RSA_public_decrypt(RSA_size(apub), buf, last_data, apub, RSA_PKCS1_PADDING); // decryption of eSign -> clear MD5 
//
//		fclose(fsig);
//			
//		// signature validation comparing the decrypted MD5 and computed one from the clear input message
//		if ( memcmp(last_data, finalDigest, 16) == 0 )
//			printf("\n Signature OK!\n");
//		else
//			printf("\n Signature is wrong!\n");
//			
//		free(last_data);
//		free(buf);
//
//		RSA_free(apub);	
//			
//    } 
//	else {
//		printf("\n Usage mode: OpenSSLProj.exe fSrc.txt eSignFsrc.txt");
//		return 1;
//    }
//
//    return 0;
//}