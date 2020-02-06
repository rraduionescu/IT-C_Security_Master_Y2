//#include <stdio.h>
//#include <malloc.h>
//#include <memory.h>
//
//#include <openssl/pem.h>
//#include <openssl/rsa.h>
//
//int main(int argc, char** argv)
//{
//	if(argc == 4) {
//		FILE* fsrc = NULL;
//		FILE* fdst = NULL;
//		FILE* frst = NULL;
//		errno_t err;
//    	
//		err = fopen_s(&fsrc, argv[1], "rb");
//		fseek(fsrc, 0, SEEK_END);
//		int fileLen = ftell(fsrc);
//		fseek(fsrc, 0, SEEK_SET);
//		
//		
//		RSA* apub;
//		RSA* apriv;
//		FILE* f;
//		
//		unsigned char* e_data = NULL;
//		unsigned char* last_data = NULL;
//
//		apriv = RSA_new();
//		apub = RSA_new();
//
//		// Encrypt the clear message stored by an input file
//		f = fopen("pubKeyFile.pem","r");
//		apub = PEM_read_RSAPublicKey(f, NULL, NULL, NULL); // RSA public key is loaded from pubKeyFile.pem
//		fclose(f);
//
//		err = fopen_s(&fdst, argv[2], "wb");
//		
//		unsigned char* fsrcbuf = (unsigned char*)malloc(RSA_size(apub) + 1);
//		fsrcbuf[RSA_size(apub)] = 0x00;
//		e_data = (unsigned char *) malloc(RSA_size(apub));
//		if (fileLen > RSA_size(apub)) {
//			while(fread_s(fsrcbuf, RSA_size(apub), sizeof(unsigned char), RSA_size(apub), fsrc) == RSA_size(apub)) { // message chunk of RSA key size is loaded from input file
//				RSA_public_encrypt(RSA_size(apub), fsrcbuf, e_data, apub, RSA_NO_PADDING); // encryption with RSA public key
//																							// no padding to be added to the clearmessage chunk
//				fwrite(e_data, sizeof(unsigned char), RSA_size(apub), fdst);
//			}
//		} 
//		else {
//			fread_s(fsrcbuf, RSA_size(apub), sizeof(unsigned char), RSA_size(apub), fsrc); // one single message chunk to be enrypted using padding
//		}
//
//		// check what it happens when the input file has the size as multiple RSA public key length butes!!!!
//		RSA_public_encrypt(fileLen % RSA_size(apub), fsrcbuf, e_data, apub, RSA_PKCS1_PADDING); // encryption for the latest message chunnk
//		fwrite(e_data, sizeof(unsigned char), RSA_size(apub), fdst);
//
//		// Decryption of the encrypted message; check against the initial input file content
//		f = fopen("privKeyFile.pem","r");
//		apriv = PEM_read_RSAPrivateKey(f, NULL, NULL, NULL);
//		fclose(f);
//
//		free(e_data);
//		e_data = (unsigned char *) malloc(RSA_size(apub));
//		last_data = (unsigned char *) malloc(RSA_size(apub));
//		fclose(fdst);
//
//		fopen_s(&fdst, argv[2], "rb");
//		fseek(fdst, 0, SEEK_END);
//		int fileLen2 = ftell(fdst);
//		fseek(fdst, 0, SEEK_SET);
//
//		int maxChunks = fileLen2 / RSA_size(apub); 
//		int currentChunk = 1; 
//
//		err = fopen_s(&frst, argv[3], "wb");
//
//		if (fileLen2 > RSA_size(apub)) {
//			while(fread_s(e_data, RSA_size(apub), sizeof(unsigned char), RSA_size(apub), fdst) == RSA_size(apub)) { 
//				if(currentChunk != maxChunks) {
//					RSA_private_decrypt(RSA_size(apub), e_data, last_data, apriv, RSA_NO_PADDING); // decryption of a full encrypted message chunk
//																									//no padding for full encrypted message chunks
//					fwrite(last_data, sizeof(unsigned char), RSA_size(apub), frst); // decrypted message block is saved in a different file
//					currentChunk++; 
//				}
//			}
//		}
//		else {
//			fread_s(e_data, RSA_size(apub), sizeof(unsigned char), RSA_size(apub), fdst);
//		}
//
//		RSA_private_decrypt(RSA_size(apub), e_data, last_data, apriv, RSA_PKCS1_PADDING); // decryption using padding for the last message block from the input (encrypte) file
//		fwrite(last_data, sizeof(unsigned char),fileLen % RSA_size(apub), frst); // fileLen is the length of the clear input message	
//
//		
//		free(last_data);
//		free(e_data);
//		free(fsrcbuf);
//
//		RSA_free(apub);
//		RSA_free(apriv);
//
//		fseek(frst, 0, SEEK_END);
//		printf("Nr. of bytes on the decryped file: %d \n", ftell(frst));
//		fseek(fsrc, 0, SEEK_END);										
//		printf("Nr. of bytes on the input file: %d", ftell(fsrc));		
//		
//		fclose(fsrc);
//		fclose(frst);
//		fclose(fdst);
//
//    } else {
//		printf("\n Usage mode: OpenSSLProj.exe f1.txt encryptf1.txt f9.txt");
//		return 1;
//    }
//
//    return 0;
//}