//#include <stdio.h>
//#include <string.h>
//
//int RleEncodeFile(char *inFile, char *outFile)
//{
//    FILE *fpIn;                         /* uncoded input */
//    FILE *     fpOut;                        /* encoded output */
//    int currChar, prevChar;             /* current and previous characters */
//    unsigned char count;                /* number of characters in a run */
//
//    /* open input and output files */
//    if ((fpIn = fopen(inFile, "rb")) == NULL)
//        return 0;
//
//    if (outFile == NULL) {
//        fpOut = stdout;     /* default to stdout */
//    } else {
//        if ((fpOut = fopen(outFile, "wb")) == NULL)
//        {
//            fclose(fpIn);
//            return 0;
//        }
//    }
//
//    /* encode inFile */
//    prevChar = -1;     /* force next char to be different */
//    count = 0;
//
//    /* read input until there's nothing left */
//    while ((currChar = fgetc(fpIn)) != -1) {
//        fputc(currChar, fpOut);
//
//        /* check for run */
//        if (currChar == prevChar) {
//            /* we have a run.  count run length */
//            count = 0;
//
//            while ((currChar = fgetc(fpIn)) != -1) {
//                if (currChar == prevChar) {
//                    count++;
//
//                    if (count == 255) {
//                        /* count is as long as it can get */
//                        fputc(count, fpOut);
//
//                        /* force next char to be different */
//                        prevChar = -1;
//                        break;
//                    }
//				}
//				else {
//                    /* run ended */
//                    fputc(count, fpOut);
//                    fputc(currChar, fpOut);
//                    prevChar = currChar;
//                    break;
//                }
//            }
//        } else {
//            /* no run */
//            prevChar = currChar;
//        }
//
//        if (currChar == -1) {
//            /* run ended because of EOF */
//            fputc(count, fpOut);
//            break;
//        }
//    }
//
//    /* close all open files */
//    fclose(fpOut);
//    fclose(fpIn);
//
//    return 1;
//}
//
//int RleDecodeFile(char *inFile, char *outFile)
//{
//    FILE *fpIn;                         /* encoded input */
//    FILE *fpOut;                        /* uncoded output */
//    int currChar, prevChar;             /* current and previous characters */
//    unsigned char count;                /* number of characters in a run */
//
//    /* open input and output files */
//    if ((fpIn = fopen(inFile, "rb")) == NULL)
//        return 0;
//    
//    if (outFile == NULL) {
//        fpOut = stdout;     /* default to stdout */
//    } else {
//        if ((fpOut = fopen(outFile, "wb")) == NULL) {
//            fclose(fpIn);
//            return 0;
//        }
//    }
//
//    /* decode inFile */
//    prevChar = -1;     /* force next char to be different */
//
//    /* read input until there's nothing left */
//    while ((currChar = fgetc(fpIn)) != -1) {
//        fputc(currChar, fpOut);
//
//        /* check for run */
//        if (currChar == prevChar) {
//            /* we have a run.  write it out. */
//            count = fgetc(fpIn);
//            while (count > 0) {
//                fputc(currChar, fpOut);
//                count--;
//            }
//            prevChar = -1;     /* force next char to be different */
//        } else {
//            /* no run */
//            prevChar = currChar;
//        }
//    }
//
//    /* close all open files */
//    fclose(fpOut);
//    fclose(fpIn);
//
//    return 1;
//}
//
//void main(int argc, char** argv) 
//{
//	if(argc < 4) 
//		printf("\n Use:\n rle1.exe -e fsrc.txt fdst.txt \n or \n rle1.exe -d fsrc.txt fdst.txt");
//
//	if(strcmp(argv[1],"-e") == 0) {
//		RleEncodeFile(argv[2], argv[3]);
//	} else if(strcmp(argv[1],"-d") == 0) {
//		RleDecodeFile(argv[2], argv[3]);
//	} else {
//		printf("\n Unknown action");
//	}
//}