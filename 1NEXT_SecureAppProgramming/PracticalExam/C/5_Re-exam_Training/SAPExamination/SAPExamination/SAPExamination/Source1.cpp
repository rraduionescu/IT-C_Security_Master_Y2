//
//#include <stdio.h>
//#include <string.h>
//#include <malloc.h>
//#include <openssl/aes.h>
//
//int main(int argc, char** argv)
//{
//
//		FILE* fSrc = NULL, * fDst = NULL;
//		errno_t err;
//		char opt[3];
//		char mode[7];
//
//		AES_KEY akey;
//		unsigned char* inBuf = NULL;
//		unsigned char* outBuf;
//		unsigned char userSymmetricKey[16] = { 'p', 'a', 'r', 'o', 'l', 'a', 'p', 'u', 't', 'e', 'r', 'n', 'i', 'c', 'a', '#' };
//
//			fopen_s(&fSrc, "Message.txt", "rb");
//			fopen_s(&fDst, "Output.enc", "wb");
//			fseek(fSrc, 0, SEEK_END);
//			long int inLen = ftell(fSrc);
//			fseek(fSrc, 0, SEEK_SET);
//			long int outLen = 0;
//			if ((inLen % 16) == 0)
//				outLen = inLen;
//			else
//				outLen = ((inLen / 16) * 16) + 16;
//
//			inBuf = (unsigned char*)malloc(outLen);
//			outBuf = (unsigned char*)malloc(outLen);
//			memset(inBuf, 0x00, outLen);
//			fread(inBuf, inLen, 1, fSrc);
//
//			AES_set_encrypt_key(userSymmetricKey, 128, &akey);
//				for (int i = 0; i < (outLen / 16); i++)
//					AES_encrypt(&(inBuf[i * 16]), &(outBuf[i * 16]), &akey);
//
//			fwrite(&inLen, sizeof(inLen), 1, fDst);
//			fwrite(outBuf, outLen, 1, fDst);
//			free(outBuf);
//			free(inBuf);
//			fclose(fDst);
//			fclose(fSrc);
//			bool a = true;
//			char ch = a;
//	return 0;
//}
