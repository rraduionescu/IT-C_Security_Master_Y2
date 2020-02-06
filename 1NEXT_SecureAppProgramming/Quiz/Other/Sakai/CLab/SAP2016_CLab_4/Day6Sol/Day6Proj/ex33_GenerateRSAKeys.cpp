//#include <stdio.h>
//#include <malloc.h>
//#include <openssl/pem.h>
//#include <openssl/rsa.h>
//
//int main()
//{
//	RSA *rsaKP = NULL;
//
//	rsaKP = RSA_generate_key(1024, 65535, NULL, NULL); 	
//	
//	RSA_check_key(rsaKP); 
//
//	FILE *fpPriv = NULL;
//	fopen_s(&fpPriv, "privKeyFile.pem","w+");
//	PEM_write_RSAPrivateKey(fpPriv, rsaKP, NULL, NULL, 0 ,0, NULL);
//	fclose(fpPriv);
//
//	FILE *fpPub = NULL;
//	fopen_s(&fpPub, "pubKeyFile.pem","w+");
//	PEM_write_RSAPublicKey(fpPub, rsaKP);
//	fclose(fpPub);
//
//	RSA_free(rsaKP);
//
//	printf("\n The RSA key pair generated! \n");
//
//	return 0;
//}