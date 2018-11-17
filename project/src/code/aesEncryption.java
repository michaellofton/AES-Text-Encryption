/**
 * Finished by Michael L. on 12/25/16.
 * Comments made by Michael L.
 * Code made by Michael L.
 *
 * The aesEncryption class creates a key and an IV with randomized bytes. It then instantiates a cipher with the key,
 * the IV, and the chosen padding type, algorithm, encrypting mode. The output is then placed into a byte array,
 * converted, and outputted as a string value in ciphertext form.
 */

package code;

//Exceptions:
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;





//exceptions:
import java.security.InvalidKeyException;


public class aesEncryption
{
    //Fields AKA Global Variables:

    //Used for encryption:
    private SecureRandom IVrandomGenerator;
    private SecretKey secretAESKey;
    private KeyGenerator aesKeyGen;
    private Cipher aesEncryptionCipher;
    private byte[] IVByteArray;
    private byte[] aesKeyBytes;
    private byte[] eCipherTextBytes;
    private byte[] encodedCiphertext;
    private byte[] prependedIvToCiphertext;
    private SecretKeySpec secretKeySpec;

    //Used for Information & Setting up Process:
    private static String VERSION = "1.0";
    private static String DEVELOPER = "Some Person";
    private static int KEY_SIZE = 128; //(this can only be 128, 192, or 256)
    private static String ALGORITHM = "AES";
    private static String MODE = "CBC";
    private static String PADDING = "PKCS5Padding";
    private String instance = ALGORITHM + "/" + MODE + "/" + PADDING; /* AES/CBC/PKCS5Padding */
    private static String TEXT_ENCODING = "Base64";
    private static String PROVIDER = "Encrypt something to find out";
                                //aesKeyGen.getProvider().toString();
    private static String IV_LOCATION = "Prepended to Ciphertext"; //"Prepended to Key", "Appended to Ciphertext", "Appended to Key"
    private static String KEY_SETTINGS = "Randomly Generated Key"; //"Typed Password Key" "Randomly Generated Key"
    private static int CIPHER_SIZE;

    //Used for decryption:
    private byte[] dCipherTextBytes;
    private boolean validKey = true;

    //Timing Encryption:
    private double eStartTime;
    private double eEndTime;

    //Timing Decryption:
    private double dStartTime;
    private double dEndTime;


    //Encryption method:
    public String aesEncrypt(String plaintext)
    {
        try {
            /*they all need to be together, or else their localization due
            to the try and catch blocks will give errors. It would be
            "Out of scope"*/

            //********************CIPHER INSTANTIATE:*************************
            aesEncryptionCipher = Cipher.getInstance(instance);
            CIPHER_SIZE = aesEncryptionCipher.getBlockSize();

            //*******************IV:**************************
            IVByteArray = new byte[CIPHER_SIZE];
            IVrandomGenerator = new SecureRandom();
            IVrandomGenerator.nextBytes(IVByteArray);

            //*****************AES KEY*************************************
            //think about random bits generated and secret key spec creation with those random bits
            //being implemented into a new secret key object creating the aes key

            aesKeyGen = KeyGenerator.getInstance(ALGORITHM);
            aesKeyGen.init(KEY_SIZE);    //if JCE isn't installed properly, giving the keysize 256 as value will give invalid key exception
                                        //keySize should be placed in init, or simply the integer amount of the key size.

            secretAESKey = aesKeyGen.generateKey(); //Generate a new key using the provider accessed from the key generator,
                                                    // and save it as a Secret Key object

            PROVIDER = aesKeyGen.getProvider().toString(); //Set the provider to whatever was just used to make the keys.

            aesKeyBytes = secretAESKey.getEncoded();

            secretKeySpec = new SecretKeySpec(aesKeyBytes, ALGORITHM);

            //**********************CIPHER ENCRYPT DATA***********************************

            aesEncryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(IVByteArray));

            //Time the Encryption Process:
            eStartTime = System.nanoTime();
            eCipherTextBytes = aesEncryptionCipher.doFinal(plaintext.getBytes("UTF-8"));
            eEndTime = System.nanoTime();

            //********************************** PRE-PEND IV TO CIPHER TEXT*************************************
            /*
            Prepend the IV Bytes to the encrypted Cipher Text bytes using System.arraycopy
            The IV can be in plain site since the IV acts just as a nonce (a random number used only once)
            IV Is similar to a salt for a hash function, it's like a salt for the encryption function.
            "Doesn't hurt the vulnerability" (Although it might help improve the system a bit if it WAS hidden or obscured)

            "IN THE FUTURE: Should be prepended/intertwined with the key since it acts very similarly to one component of the key"
            Might actually compromise security slightly, think about if a hacker knew the salt used in a system that hashes
            passwords. IF the hacker knew the salt, they could use that salt combined with the known hash algorithm to create
            a completely accurate hash table/rainbow table lookup of many common passwords. The salt's added security is
            undermined because it's known, thus it is similar to a key and must be kept secret, thus it must be kept with
            the key (appended or prepended) and the whole key/iv must be kept hidden/secret. A hacker is only given the key size,
            the algorithm, and the ciphertext. It must be impossible for them to figure out any other information from
            what they're already given. If you give them ciphertext with an IV that is (pre/ap)pended, you're giving them more
            clues/information and helping them out.

            The hacker can figure out that the ciphertext is encoded with Base64 and easily decode it. Then they can
            figure out that the IV Bytes are prepended to the ciphertext and extract them both individually. Now they
            have 2 of the 3 needed pieces to decrypt the data. We want to only give them ONE, the ciphertext.
             */

            //Make new Byte Array [Our Destination]
            prependedIvToCiphertext = new byte[IVByteArray.length + eCipherTextBytes.length];
            //Copy the IVByteArray, starting from position (0) to our destination (prependedIvToCiphertext)
            // and place it at the starting position of the destination (0), using the length of the IVByteArray to determine
            // how much of the IVByteArray we should copy (we use the length to include the entire IVByteArray)
            //Also, IVByteArray should always be 16 bytes since AES encryption's IV is the size of the block, which is always 16 bytes
            //Copy source, starting position of source, copy destination, position to insert in destination, amount to copy from source
            System.arraycopy(IVByteArray, 0, prependedIvToCiphertext, 0, IVByteArray.length);

            //Copy the eCipherTextBytes, starting from position (0), to our destination (prependedIvToCiphertext)
            // and place it at the IVByteArray.length position of the destination (ENDING position (.length) of the IVByte Array),
            // using the entire length of the eCipherTextBytes to determine how much of the encrypted cipher text bytes we should copy
            System.arraycopy(eCipherTextBytes, 0, prependedIvToCiphertext, IVByteArray.length, eCipherTextBytes.length);

            encodedCiphertext = Base64.getEncoder().encode(prependedIvToCiphertext);



            //********************Catch blocks***********************************************
        }
        catch(NoSuchPaddingException ex)
        {
            System.out.println("[AES] The padding type selected when instantiating the cipher is unavailable.");
            ex.printStackTrace();
        }
        catch(NoSuchAlgorithmException ex)
        {
            System.out.println("[AES] The algorithm type selected when instantiating the cipher is unavailable. AND/OR");
            System.out.println("[AES] The intended algorithm to instantiate the random generator is unavailable. AND/OR");
            System.out.println("[AES] The algorithm desired to instantiate the key generator is unavailable.");
            ex.printStackTrace();
        }
        catch(InvalidKeyException ex)
        {
            System.out.println("[AES] Failed to initialize cipher. The key used to initialize the cipher is invalid.");
            System.out.println("Key size used: " + KEY_SIZE);
            ex.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException ex)
        {
            System.out.println("[AES] Failed to initialize cipher. There is an Invalid Algorithm Parameter somewhere.");
            ex.printStackTrace();
        }
        catch(UnsupportedEncodingException ex)
        {
            System.out.println("[AES] The specified encoding type is not supported, and has thus failed.");
            ex.printStackTrace();
        }
        catch(IllegalBlockSizeException ex)
        {
            System.out.println("[AES] The block size that was used is wrong in size. Please change the size.");
            ex.printStackTrace();
        }
        catch(BadPaddingException ex)
        {
            System.out.println("[AES] The padding that was used to encrypt the message " + "\n"
                    + "was setup incorrectly and should be changed");
            ex.printStackTrace();
        }

        //******************************RETURNING CIPHERTEXT***************************************
        return new String(encodedCiphertext);
    }

    /**
     * Method used to decrypt an encrypted message in ciphertext form.
     * @param aesKey is received from the sender. It's in the form of a SecretKey. This is the AES key in byte form.
     * @param cipherTextString is the IV and message in encrypted form as a string. It is received in the key from the sender.
     * @return returns the decrypted ciphertext in a string form as the original plaintext.
     */
    public String aesDecrypt(byte[] aesKey, String cipherTextString)
    {
        try
        {
        //************************INSTANTIATE NEW CIPHER**********************************
            Cipher aesDecryptionCipher = Cipher.getInstance(instance);
            CIPHER_SIZE = aesDecryptionCipher.getBlockSize();

        //************************CREATE KEY, EXTRACT IV BYTES, EXTRACT CIPHERTEXT BYTES**********************************

            //Create a new secret key from the given key bytes:
            SecretKeySpec secretKey = new SecretKeySpec(aesKey, ALGORITHM);

            //Decode the given ciphertext into it's byte form:
            byte[] decodedCipherTextBytes = Base64.getDecoder().decode(cipherTextString);

            //Extract the IV Byte array from the ciphertext
            //Copy FROM decodedBytes at position 0 TO position CIPHER_SIZE
            byte[] dIVByteArray = Arrays.copyOfRange(decodedCipherTextBytes, 0, CIPHER_SIZE);

            //Extract the real ciphertext bytes:
            byte[] cipherTextBytes = Arrays.copyOfRange(decodedCipherTextBytes, CIPHER_SIZE, decodedCipherTextBytes.length);

            //*************************INITIALIZE DECRYPTION MODE*****************************

            aesDecryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(dIVByteArray));

            //*********************************DECRYPT MESSAGE***************************************

            //Time the Decryption Process:
            dStartTime = System.nanoTime();
                //dCipherTextBytes = aesDecryptionCipher.doFinal(Base64.getDecoder().decode(cipherTextString));
            dCipherTextBytes = aesDecryptionCipher.doFinal(cipherTextBytes);
            dEndTime = System.nanoTime();
        }
        catch (NoSuchAlgorithmException ex)
        {
            System.out.println("[AES] The algorithm used to instantiate the decryption cipher isn't available.");
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (NoSuchPaddingException ex)
        {
            System.out.println("[AES] The algorithm used to instantiate the padding for the decryption cipher isn't available.");
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (InvalidKeyException ex)
        {
            System.out.println("[AES] The key used to initialize the decryption cipher is invalid.");
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (InvalidAlgorithmParameterException ex)
        {
            System.out.println("[AES] There is an invalid algorithm parameter used in the decrypting process");
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (IllegalBlockSizeException ex)
        {
            System.out.println("[AES] The block size used to decrypt the ciphertext at the cipher is illegal and must be changed.");
            System.out.println("Decryption Ciphertext Bytes: " + dCipherTextBytes);
            System.out.println("AES Ciphertext length: " + dCipherTextBytes.length); //Won't print anything if it's null
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (BadPaddingException ex)
        {
            System.out.println("[AES] The padding used to decrypt the message in the cipher is bad. Change it.");
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            validKey = false;
            return "";
        }
        /*
        catch (IOException ex)
        {
            System.out.println("[AES] There's been an error trying to decode the ciphertext from BASE64 format into bytes.");
            ex.printStackTrace();
        }*/

//**************************************RETURNING DECRYPTED STRING******************************************************
        return new String(dCipherTextBytes);
    }


    //Getters and makers
    public String getKey()
    {
        //Transmit the key's bytes using base 64 encoding
        return new String(Base64.getEncoder().encode(aesKeyBytes));
        //Note: Any of these work:
        //secretAESKey.getEncoded()
        //secretKeySpec.getEncoded()
        //aesKeyBytes
    }
    public String getIV()
    {
        //Transmit the IV's bytes using base 64 encoding
        return new String(Base64.getEncoder().encode(IVByteArray));
    }


    public byte[] decodeSecretKeyBytes(byte[] aesObjKeyBytes)
    {
        byte[] decodedBytes = new byte[0];
        try
        {
            decodedBytes = Base64.getDecoder().decode(aesObjKeyBytes);
        }
        catch (Exception ex)
        {
            validKey = false;
            return new byte[0];
        }
        return decodedBytes;
    }
    public byte[] decodeIVBytes(byte[] aesObjIvBytes)
    {
        return Base64.getDecoder().decode(aesObjIvBytes);
    }


    public String getEncryptionTimeMilliSec()
    {
        //Miliseconds: miliseconds = nanoseconds/1000000

        double eTotalTime = (eEndTime - eStartTime)/(1000000); //  /(1000000)
        return Double.toString(eTotalTime);
    }
    public String getDecryptionTimeMilliSec()
    {
        double dTotalTime = (dEndTime - dStartTime)/(1000000); //  /(1000000)
        return Double.toString(dTotalTime);
    }


    public static String getVersion()
    {
        return VERSION;
    }
    public static String getDeveloper()
    {
        return DEVELOPER;
    }
    public static String getKeySize()
    {
        return Integer.toString(KEY_SIZE);
    }
    public static String getAlgorithm()
    {
        return ALGORITHM;
    }
    public static String getMode()
    {
        return MODE;
    }
    public static String getPadding()
    {
        return PADDING;
    }
    public static String getProvider()
    {
        return PROVIDER;
    }
    public static String getTextEncoding()
    {
        return TEXT_ENCODING;
    }
    public static String getIvLocation()
    {
        return IV_LOCATION;
    }
    public static String getCipherSize()
    {
        return Integer.toString(CIPHER_SIZE);
    }
    public static String getKeySettings()
    {
        return KEY_SETTINGS;
    }

    public boolean isValidKey()
    {
        return validKey;
    }

}
