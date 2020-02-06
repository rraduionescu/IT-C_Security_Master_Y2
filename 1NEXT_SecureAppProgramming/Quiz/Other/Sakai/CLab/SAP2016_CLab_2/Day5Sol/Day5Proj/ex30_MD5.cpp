//#include <stdio.h>
//#include <malloc.h>
//#include <openssl/md5.h>
//
//#define ADD_STRING_LENGTH 16
//#define MESSAGE_CHUNK 128
//
//int main(int argc, char**argv)
//{
//    if(argc == 2) {
//
//		FILE* f = NULL;
//		errno_t err;
//		MD5_CTX ctx;
//
//		// 1st version to get MD5
//		unsigned char finalDigest[MD5_DIGEST_LENGTH];
//		MD5_Init(&ctx);
//	
//		unsigned char* fileBuffer = NULL;
//
//		err = fopen_s(&f, argv[1], "rb");
//		if(err == 0) {	    
//			fseek(f, 0, SEEK_END);
//			int fileLen = ftell(f);
//			fseek(f, 0, SEEK_SET);
//
//			fileBuffer = (unsigned char*)malloc(fileLen);
//			fread(fileBuffer, fileLen, 1, f);
//			unsigned char* tmpBuffer = fileBuffer;
//
//			while (fileLen > 0) {
//				if (fileLen > MESSAGE_CHUNK) {
//					MD5_Update(&ctx, tmpBuffer, MESSAGE_CHUNK);																			
//				} 
//				else {
//					MD5_Update(&ctx, tmpBuffer, fileLen);
//				}
//				fileLen -= MESSAGE_CHUNK;
//				tmpBuffer += MESSAGE_CHUNK;
//			}
//
//    		//MD5_Update(&ctx, fileBuffer, fileLen);
//    	
//			MD5_Final(finalDigest, &ctx);			
//
//			int count = 0;
//			printf("\nMD = ");
//    		for( int i=0; i<MD5_DIGEST_LENGTH; i++) {
//        		printf( "%2X", finalDigest[i] );        		
//            		printf( " " );
//    		}
//			
//			// 2nd version of MD5 for 16 bytes-length input buffer
//			unsigned char addChars[ADD_STRING_LENGTH];
//			for (int i = 0; i < ADD_STRING_LENGTH; i++)
//				addChars[i] = i + 15;
//			printf("\n%s\n", addChars);
//			MD5_Transform(&ctx, addChars);										
//			MD5_Final(finalDigest, &ctx);
//
//			count = 0;
//			printf("\nMD = ");
//    		for( int i=0; i<MD5_DIGEST_LENGTH; i++) {
//        		printf( "%2X", finalDigest[i] );        		
//            		printf( " " );
//    		}
//
//			printf("\n");
//			
//			// 3rd version of MD5 for 16 bytes-length input buffer
//			unsigned char *result = 0;
//			result = MD5(addChars, ADD_STRING_LENGTH, finalDigest);		
//		
//			count = 0;
//			printf("\nMD = ");
//    		for( int i=0; i<MD5_DIGEST_LENGTH; i++) {
//        		printf( "%2X", finalDigest[i] );        		
//            		printf( " " );
//    		}
//
//			count = 0;
//			printf("\nResult is: ");
//    		for( int i=0; i<MD5_DIGEST_LENGTH; i++) {
//        		printf( "%2X", result[i] );        		
//            		printf( " " );
//    		}
//			printf("\n");
//
//			fclose(f);
//		}
//
//    } 
//	else {
//		printf("\n Usage Mode: ProgMainMD5.exe fSrc.txt");
//		return 1;
//    }
//
//    return 0;
//}
