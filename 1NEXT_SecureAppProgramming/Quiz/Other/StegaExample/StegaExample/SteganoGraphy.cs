using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace StegaExample
{
    public class SteganoGraphy
    {
        public static void HideMessageInBitmap(Stream messageStream, Bitmap bitmap, Stream keyStream, bool useGrayscale)
        {
            HideOrExtract(ref messageStream, bitmap, keyStream, false, useGrayscale);
            messageStream = null;
        }

        public static void ExtractMessageFromBitmap(Bitmap bitmap, Stream keyStream, ref Stream messageStream)
        {
            HideOrExtract(ref messageStream, bitmap, keyStream, true, false);
        }

        private static void HideOrExtract(ref Stream messageStream, Bitmap bitmap,
            Stream keyStream, bool extract, bool useGrayScale)
        {
            int currentStepWidth = 0;
            byte currentKeyByte;
            byte currentReverseKeyByte;
            long keyPosition;
            int dispersion = 1;
            int bitmapWidth = bitmap.Width;
            int bitmapHeight = bitmap.Height;
            int currentColor = 0;
            Color pixelColor;
            Int32 messageLength = 0;

            if (extract == false)
            {
                messageLength = (Int32)messageStream.Length;
                if(messageStream.Length >= 16777215)
                {
                    String exception = "Message too long, must be " +
                        "lesser than 2^24 bytes.";
                    throw new Exception(exception);
                }
                long countPixels = (bitmapHeight * bitmapWidth) - 1;
                long countRequiredPixels = 1;
                while((keyStream.Position < keyStream.Length)
                    &&(keyStream.Position < messageLength))
                {
                    countRequiredPixels += keyStream.ReadByte();
                }
                countRequiredPixels *= (long)System.Math.Ceiling
                    (((float)messageLength / (float)keyStream.Length));
                dispersion = (int)Math.Truncate
                    (((float)countPixels / (float)countRequiredPixels));
                if(countRequiredPixels > countPixels)
                {
                    String exception = "Image too small, must have at least " +
                        countRequiredPixels + " pixels";
                    throw new Exception(exception);
                }
                int colorValue = messageLength;
                int red = colorValue >> 2;
                colorValue -= red << 2;
                int green = colorValue >> 1;
                int blue = colorValue - (green << 1);
                pixelColor = Color.FromArgb(red, green, blue);
                bitmap.SetPixel(0, 0, pixelColor);
            }
            else
            {
                //this is for the decoding phase
                keyStream.Seek(0, SeekOrigin.Begin);
                pixelColor = bitmap.GetPixel(0, 0);
                messageLength = (pixelColor.R << 2) + (pixelColor.G << 1);
                long countPixels = (bitmapHeight * bitmapWidth) - 1;
                long countRequiredPixels = 1;
                while ((keyStream.Position < keyStream.Length)
                    && (keyStream.Position < messageLength))
                {
                    countRequiredPixels += keyStream.ReadByte();
                }
                countRequiredPixels *= (long)System.Math.Ceiling
                    (((float)messageLength / (float)keyStream.Length));
                //dispersion = (int)Math.Truncate
                //    (((float)countPixels / (float)countRequiredPixels));
                dispersion = (int)Math.Truncate
                    (((float)countPixels / (float)countRequiredPixels));
                messageStream = new MemoryStream(messageLength);
            }
            keyStream.Seek(0, SeekOrigin.Begin);
            messageStream.Seek(0, SeekOrigin.Begin);
            Point pixelPosition = new Point(1, 0);

            for(int messageIndex =0; messageIndex<messageLength;
                messageIndex++)
            {
                if (keyStream.Position == keyStream.Length)
                    keyStream.Seek(0, SeekOrigin.Begin);
                currentKeyByte = (byte)keyStream.ReadByte();
                currentStepWidth = (currentKeyByte == 0) ?
                    (byte)1 : currentKeyByte * dispersion;

                keyPosition = keyStream.Position;
                keyStream.Seek(-keyPosition, SeekOrigin.End);
                currentReverseKeyByte = (byte)keyStream.ReadByte();
                keyStream.Seek(keyPosition, SeekOrigin.Begin);
                
                while(currentStepWidth > bitmapWidth)
                {
                    currentStepWidth -= bitmapWidth;
                    pixelPosition.Y++;
                }
                if((bitmapWidth - pixelPosition.X) < currentStepWidth)
                {
                    pixelPosition.X = currentStepWidth - 
                        (bitmapWidth - pixelPosition.X);
                    pixelPosition.Y++;
                }
                else
                {
                    pixelPosition.X += currentStepWidth;
                }
                pixelColor = bitmap.GetPixel(pixelPosition.X,
                    pixelPosition.Y);
                if(extract == false)
                {
                    int currentByte = messageStream.ReadByte() ^
                        currentReverseKeyByte;
                    if (useGrayScale)
                        pixelColor = Color.FromArgb
                            (currentByte, currentByte, currentByte);
                    else
                    {

                        SetColorComponent(ref pixelColor,
                            currentColor, currentByte);
                    }
                    bitmap.SetPixel(pixelPosition.X, pixelPosition.Y, pixelColor);
                }
                else
                {
                    byte value = GetColorComponent(pixelColor, currentColor);
                    byte currentByte = (byte)(currentReverseKeyByte ^ value);
                    messageStream.WriteByte(currentByte);
                }
                currentColor = (currentColor == 2) ? 0 : (currentColor + 1);
            }
            bitmap = null;
            keyStream = null;

        }
        private static byte GetColorComponent(Color pixelColor, int colorComponent)
        {
            byte returnValue = 0;
            switch (colorComponent)
            {
                case 0:
                    returnValue = pixelColor.R;
                    break;
                case 1:
                    returnValue = pixelColor.G;
                    break;
                case 2:
                    returnValue = pixelColor.B;
                    break;
            }
            return returnValue;
        }

        private static void SetColorComponent(ref Color pixelColor, int colorComponent, int newValue)
        {
            switch (colorComponent)
            {
                case 0:
                    pixelColor = Color.FromArgb(newValue, pixelColor.G, pixelColor.B);
                    break;
                case 1:
                    pixelColor = Color.FromArgb(pixelColor.R, newValue, pixelColor.B);
                    break;
                case 2:
                    pixelColor = Color.FromArgb(pixelColor.R, pixelColor.G, newValue);
                    break;
            }
        }
    }
}
