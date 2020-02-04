import java.lang.Exception
import java.util.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

class CryptoJAES
{
    fun AESCryptoECB(inputData: ByteArray, key: ByteArray, mode: Int): ByteArray?
    {
        try
        {
            val cipher = Cipher.getInstance("AES/ECB/NoPadding")
            val secretKeySpec = SecretKeySpec(key, "AES")

            if(mode == 0)
            {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
            }
            else
            {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)
            }

            return cipher.doFinal(inputData)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        return null
    }

    fun AESCryptoCBC(inputData: ByteArray, key: ByteArray, ivs: ByteArray, mode: Int): ByteArray?
    {
        try
        {
            val cipher = Cipher.getInstance("AES/CBC/NoPadding")
            val secretKeySpec = SecretKeySpec(key, "AES")

            val iv = IvParameterSpec(ivs)

            if(mode == 0)
            {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv)
            }
            else
            {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv)
            }

            return cipher.doFinal(inputData)
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        return null
    }
}

fun main(args: Array<String>)
{
    // AES ECB
    val testText = "Hey! Java/Kotlin"
    val password = "password#1234567"

    var c = CryptoJAES()
    var encTextBytesEcb = c.AESCryptoECB(testText.toByteArray(), password.toByteArray(), 0)

    println("Test ENC AES ECB Base64 = " + Base64.getEncoder().encodeToString(encTextBytesEcb))

    var decTextBytesEcb = c.AESCryptoECB(encTextBytesEcb!!, password.toByteArray(), 1)

    println("Test DEC AES ECB Base64 = " + decTextBytesEcb!!.toString(Charsets.ISO_8859_1))

    // AES CBC
    var IV = "1234567812345678"
    var encTextBytesCbc = c.AESCryptoCBC(testText.toByteArray(), password.toByteArray(), IV.toByteArray(), 0)

    println("Test ENC AES CBC Base64 = " + Base64.getEncoder().encodeToString(encTextBytesCbc))

    var decTextBytesCbc = c.AESCryptoCBC(encTextBytesCbc!!, password.toByteArray(), IV.toByteArray(), 1)

    println("Test DEC AES CBC Base64 = " + decTextBytesCbc!!.toString(Charsets.ISO_8859_1))
}