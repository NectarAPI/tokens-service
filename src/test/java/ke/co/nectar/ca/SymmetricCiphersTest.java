package ke.co.nectar.ca;

import ke.co.nectar.ca.keys.symmetric.AES128Cipher;
import ke.co.nectar.ca.keys.symmetric.DESCipher;
import ke.co.nectar.ca.keys.symmetric.DesedeCipher;
import ke.co.nectar.ca.keys.symmetric.SymmetricCipher;
import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.Assert.*;

public class SymmetricCiphersTest {

    @Before
    public void setUp() {}

    @After
    public void tearDown() {}

    // -- DES
    @Test
    public void testThatDESKeyWithSpecificBytesIsGenerated()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        Key expectedDESKey = generateKey(keyBytes, keyBytes.length, "DES/ECB/PKCS5Padding") ;
        DESCipher generatedDESCipher = new DESCipher(keyBytes) ;
        assertEquals("test correct DES key is generated",
                new SecretKeySpec(expectedDESKey.getEncoded(), "DES/ECB/PKCS5Padding"),
                new SecretKeySpec(generatedDESCipher.getEncoded(), "DES/ECB/PKCS5Padding"));
    }

    @Test
    public void testThatRandomDESKeyIsGenerated() {
        final int NO_OF_BYTES = 16 ;
        DESCipher generatedDESCipher = new DESCipher();
        assertNotNull("test that the key generated is not null", generatedDESCipher.getEncoded());
        assertEquals("test that correct number of bytes are generated", NO_OF_BYTES,  generatedDESCipher.getEncoded().length);
    }

    @Test
    public void testThatDESKeysWithRequiredOperatingModesAreCreated() {
        final SymmetricCipher.OperatingMode EXPECTED_OPERATING_MODE = SymmetricCipher.OperatingMode.ECB ;
        DESCipher generatedDESCipher = new DESCipher();
        generatedDESCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB); ;
        assertEquals("test the the correct operating mode has been set", EXPECTED_OPERATING_MODE,
                generatedDESCipher.getOperatingMode());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        generatedDESCipher = new DESCipher(keyBytes, SymmetricCipher.OperatingMode.ECB);
        assertEquals("test the the correct operating mode has been set", SymmetricCipher.OperatingMode.ECB,
                generatedDESCipher.getOperatingMode());
    }

    @Test
    public void testThatDESKeyWithRequiredPaddingModesAreCreated() {
        final SymmetricCipher.PaddingType NO_PADDING = SymmetricCipher.PaddingType.NoPadding;
        DESCipher generatedDESCipher = new DESCipher();
        generatedDESCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        generatedDESCipher.setPaddingType(NO_PADDING);
        assertEquals("test the the correct padding type has been set", NO_PADDING, generatedDESCipher.getPaddingType());

        final SymmetricCipher.PaddingType PKCS5_PADDING = SymmetricCipher.PaddingType.PKCS5Padding;
        generatedDESCipher = new DESCipher();
        generatedDESCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        generatedDESCipher.setPaddingType(NO_PADDING);
        generatedDESCipher.setPaddingType(SymmetricCipher.PaddingType.PKCS5Padding);
        assertEquals("test the the correct padding type has been set", PKCS5_PADDING, generatedDESCipher.getPaddingType());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        generatedDESCipher = new DESCipher(keyBytes, SymmetricCipher.OperatingMode.ECB, SymmetricCipher.PaddingType.PKCS5Padding);
        assertEquals("test the the correct padding type has been set", PKCS5_PADDING,
                generatedDESCipher.getPaddingType());
    }

    @Test
    public void testThatDESEncryptionNoPaddingOnDataCorrectlyEncryptedNoIv() {
        try {
            byte[] encryptionKey = { 0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78 };
            byte[] input = {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                    0x07, 0x08, 0x11, 0x12, 0x13, 0x14,
                    0x15, 0x16, 0x17, 0x18 } ;
            /* ECB mode does not use an initialization vector */
            DESCipher desCipher = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.ECB, SymmetricCipher.PaddingType.NoPadding);
            byte[] encryptedData = desCipher.encrypt(input);
            byte[] decryptedData = desCipher.decrypt(encryptedData);
            assertEquals("test DES encrypts and decrypts data, no IV",
                                new String(Hex.encode(input)),
                                new String(Hex.encode(decryptedData)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatDESEncryptionNoPaddingOnDataCorrectlyEncrypted() {
        try {
            byte[] encryptionKey = {0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78};
            byte[] input = {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                    0x07, 0x08, 0x11, 0x12, 0x13, 0x14,
                    0x15, 0x16, 0x17, 0x18};
            byte[] iv = new byte[]{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
            DESCipher desCipher = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.NoPadding);
            desCipher.setIv(iv); /* CBC mode must have an initialization vector */
            byte[] encryptedData = desCipher.encrypt(input);
            byte[] decryptedData = desCipher.decrypt(encryptedData);
            assertEquals("test DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            DESCipher desCipher2 = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.NoPadding);
            desCipher2.useRandomIv(); /* CBC mode must have an initialization vector */
            encryptedData = desCipher2.encrypt(input);
            decryptedData = desCipher2.decrypt(encryptedData);
            assertEquals("test DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatDESEncryptionWithPaddingOnDataCorrectlyEncrypted() {
        try {
            byte[] encryptionKey = {0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78};
            byte[] input = {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                    0x07, 0x08, 0x11, 0x12, 0x13, 0x14,
                    0x15, 0x16, 0x17, 0x18 };
            byte[] iv = new byte[]{ 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
            DESCipher desCipher = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS5Padding);
            desCipher.setIv(iv); /* CBC mode must have an initialization vector */
            byte[] encryptedData = desCipher.encrypt(input);
            byte[] decryptedData = desCipher.decrypt(encryptedData);
            assertEquals("test DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            DESCipher desCipher2 = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
            desCipher2.useRandomIv(); /* CBC mode must have an initialization vector */
            encryptedData = desCipher2.encrypt(input);
            decryptedData = desCipher2.decrypt(encryptedData);
            assertEquals("test DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    // -- AES
    @Test
    public void testThatAESKeyWithSpecificBytesIsGenerated()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        Key expectedAESKey = generateKey(keyBytes, keyBytes.length, "AES") ;
        AES128Cipher generatedAESCipher = new AES128Cipher(keyBytes) ;
        assertEquals("test correct AES key is generated", new SecretKeySpec(expectedAESKey.getEncoded(), "AES"),
                                                          new SecretKeySpec(generatedAESCipher.getEncoded(), "AES"));
    }

    @Test
    public void testThatRandomAESKeyIsGenerated() {
        final int NO_OF_BYTES = 16 ;
        AES128Cipher generatedAESCipher = new AES128Cipher();
        assertNotNull("test that the key generated is not null", generatedAESCipher.getEncoded());
        assertEquals("test that correct number of bytes are generated", NO_OF_BYTES,  generatedAESCipher.getEncoded().length);
    }

    @Test
    public void testThatAESKeysWithRequiredOperatingModesAreCreated() {
        final SymmetricCipher.OperatingMode ECB_OPERATING_MODE = SymmetricCipher.OperatingMode.ECB ;
        AES128Cipher generatedAESCipher = new AES128Cipher();
        generatedAESCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        assertEquals("test the the correct operating mode has been set", ECB_OPERATING_MODE,
                generatedAESCipher.getOperatingMode());

        final SymmetricCipher.OperatingMode CBC_OPERATING_MODE = SymmetricCipher.OperatingMode.CBC ;
        generatedAESCipher = new AES128Cipher();
        generatedAESCipher.setOperatingMode(SymmetricCipher.OperatingMode.CBC);
        assertEquals("test the the correct operating mode has been set", CBC_OPERATING_MODE,
                generatedAESCipher.getOperatingMode());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        generatedAESCipher = new AES128Cipher(keyBytes, SymmetricCipher.OperatingMode.CBC);
        assertEquals("test the the correct operating mode has been set", SymmetricCipher.OperatingMode.CBC,
                generatedAESCipher.getOperatingMode());
    }

    @Test
    public void testThatAESKeyWithRequiredPaddingModesAreCreated() {
        final SymmetricCipher.PaddingType PKCS7_PADDING_TYPE = SymmetricCipher.PaddingType.PKCS7Padding;
        AES128Cipher generatedAESCipher = new AES128Cipher();
        generatedAESCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        generatedAESCipher.setPaddingType(SymmetricCipher.PaddingType.PKCS7Padding);
        assertEquals("test the the correct padding type has been set", PKCS7_PADDING_TYPE,
                generatedAESCipher.getPaddingType());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        final SymmetricCipher.PaddingType PKCS5_PADDING_TYPE = SymmetricCipher.PaddingType.PKCS5Padding;
        generatedAESCipher = new AES128Cipher(keyBytes, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS5Padding);
        assertEquals("test the the correct padding type been set", PKCS5_PADDING_TYPE,
                generatedAESCipher.getPaddingType());
    }

    @Test
    public void testThatAES128CipherEncryptionDecryption() {
        try {
            byte[] encryptionKey = {
                    0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78,
                    0x11, 0x22, 0x33, 0x44, 0x55, 0x65, 0x76, 0x10};
            byte[] input = {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                    0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18};
            byte[] iv = new byte[]{ 0x01, 0x02, 0x30, 0x04, 0x70, 0x52, 0x11, 0x20,
                                    0x11, 0x22, 0x33, 0x44, 0x55, 0x65, 0x76, 0x10 };

            // CBC mode, PKCS7 padding
            AES128Cipher aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
            aes128Cipher.setIv(iv);
            byte[] encryptedData = aes128Cipher.encrypt(input);
            byte[] decryptedData = aes128Cipher.decrypt(encryptedData);
            assertEquals("test AES 128 encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // CTR mode, PKCS5 padding
            aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.CTR, SymmetricCipher.PaddingType.PKCS5Padding);
            aes128Cipher.setIv(iv);
            encryptedData = aes128Cipher.encrypt(input);
            decryptedData = aes128Cipher.decrypt(encryptedData);
            assertEquals("test AES 128 encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // CTR mode, PKCS5 padding, Random IV
            aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.CTR, SymmetricCipher.PaddingType.PKCS5Padding);
            aes128Cipher.useRandomIv();
            encryptedData = aes128Cipher.encrypt(input);
            decryptedData = aes128Cipher.decrypt(encryptedData);
            assertEquals("test AES 128 encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // GCM mode, PKCS5Padding
            aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.GCM, SymmetricCipher.PaddingType.NoPadding);
            aes128Cipher.setIv(iv);
            encryptedData = aes128Cipher.encrypt(input);
            decryptedData = aes128Cipher.decrypt(encryptedData);
            assertEquals("test AES 128 encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // ECB Mode
            /* data must be in 16 byte blocks since AES encrypts/decrypts in 16 byte blocks */
            aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.ECB, SymmetricCipher.PaddingType.NoPadding);
            encryptedData = aes128Cipher.encrypt(input);
            decryptedData = aes128Cipher.decrypt(encryptedData);
            assertEquals("test AES 128 encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    // -- 3DES
    @Test
    public void testThatDESCEDEKeyWithSpecificBytesIsGenerated()
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06,
                0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d };
        Key expectedDesedeKey = generateKey(keyBytes, keyBytes.length, "Desede") ;
        DesedeCipher generatedDesedeCipher = new DesedeCipher(keyBytes) ;
        assertEquals("test correct 3DES key is generated",
                new SecretKeySpec(expectedDesedeKey.getEncoded(), "DESede"),
                new SecretKeySpec(generatedDesedeCipher.getEncoded(), "DESede"));
    }

    @Test
    public void testThatRandomDesedeKeyIsGenerated() {
        final int EXPECTED_NO_OF_BYTES = 112 ;
        DesedeCipher generated3DESCipher = new DesedeCipher();
        assertNotNull("test that the key generated is not null", generated3DESCipher.getEncoded());
        assertEquals("test that correct number of bytes are generated", EXPECTED_NO_OF_BYTES,  generated3DESCipher.getEncoded().length);
    }

    @Test
    public void testThatDesedeKeysWithRequiredOperatingModeAreCreated() {
        final SymmetricCipher.OperatingMode ECB_OPERATING_MODE = SymmetricCipher.OperatingMode.ECB ;
        DesedeCipher generatedDesedeCipher = new DesedeCipher();
        generatedDesedeCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        assertEquals("test the the correct operating mode has been set", ECB_OPERATING_MODE,
                generatedDesedeCipher.getOperatingMode());

        final SymmetricCipher.OperatingMode CBC_OPERATING_MODE = SymmetricCipher.OperatingMode.CBC ;
        generatedDesedeCipher = new DesedeCipher();
        generatedDesedeCipher.setOperatingMode(SymmetricCipher.OperatingMode.CBC);
        assertEquals("test the the correct operating mode has been set", CBC_OPERATING_MODE,
                generatedDesedeCipher.getOperatingMode());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        generatedDesedeCipher = new DesedeCipher(keyBytes, SymmetricCipher.OperatingMode.CBC);
        assertEquals("test the the correct operating mode has been set", SymmetricCipher.OperatingMode.CBC,
                generatedDesedeCipher.getOperatingMode());
    }

    @Test
    public void testThatDesedeKeysWithRequiredPaddingModesAreCreated() {
        final SymmetricCipher.PaddingType PKCS5_PADDING_TYPE = SymmetricCipher.PaddingType.PKCS5Padding;
        DesedeCipher generatedDesedeCipher = new DesedeCipher();
        generatedDesedeCipher.setOperatingMode(SymmetricCipher.OperatingMode.ECB);
        generatedDesedeCipher.setPaddingType(SymmetricCipher.PaddingType.PKCS5Padding);
        assertEquals("test the the correct padding type has been set", PKCS5_PADDING_TYPE,
                generatedDesedeCipher.getPaddingType());

        byte[] keyBytes = new byte[] {
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17 };
        generatedDesedeCipher = new DesedeCipher(keyBytes, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS5Padding);
        assertEquals("test the the correct padding type has been set", PKCS5_PADDING_TYPE,
                generatedDesedeCipher.getPaddingType());
    }

    @Test
    public void testThatDESedeCipherEncryptionDecryption() {
        try {
            byte[] encryptionKey = {
                    0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78,
                    0x11, 0x22, 0x33, 0x44, 0x55, 0x65, 0x76, 0x10};
            byte[] input = {
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08,
                    0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18};
            byte[] iv = new byte[]{ 0x01, 0x02, 0x30, 0x04, 0x05, 0x06, 0x07, 0x08 };

            // with configured IV
            DesedeCipher desedeCipher = new DesedeCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
            desedeCipher.setIv(iv);
            byte[] encryptedData = desedeCipher.encrypt(input);
            byte[] decryptedData = desedeCipher.decrypt(encryptedData);
            assertEquals("test Triple DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // with random IV
            DesedeCipher desedeCipher2 = new DesedeCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
            desedeCipher2.useRandomIv() ;
            encryptedData = desedeCipher2.encrypt(input);
            decryptedData = desedeCipher2.decrypt(encryptedData);
            assertEquals("test Triple DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

            // with no IV
            desedeCipher = new DesedeCipher(encryptionKey, SymmetricCipher.OperatingMode.ECB, SymmetricCipher.PaddingType.PKCS7Padding);
            encryptedData = desedeCipher.encrypt(input);
            decryptedData = desedeCipher.decrypt(encryptedData);
            assertEquals("test Triple DES encrypts and decrypts data ", new String(Hex.encode(input)), new String(Hex.encode(decryptedData)));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatDESedePropertiesAreReturnedCorrectly() {
        byte[] encryptionKey = {
                0x01, 0x12, 0x23, 0x34, 0x45, 0x56, 0x67, 0x78,
                0x11, 0x22, 0x33, 0x44, 0x55, 0x65, 0x76, 0x10};
        byte[] iv = new byte[]{ 0x01, 0x02, 0x30, 0x04, 0x05, 0x06, 0x07, 0x08 };

        // -- DESede
        // with configured IV
        DesedeCipher desedeCipher = new DesedeCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        desedeCipher.setIv(iv);
        assertEquals("test that the returned key type data is correct", "DESede", desedeCipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", desedeCipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", desedeCipher.getProperty("paddingType"));
        assertEquals("test that the returned iv data is correct", new String(Hex.encode(iv)), desedeCipher.getProperty("iv")) ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), desedeCipher.getProperty("key"));

        // with random IV
        desedeCipher = new DesedeCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        desedeCipher.useRandomIv();
        assertEquals("test that the returned key type data is correct", "DESede", desedeCipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", desedeCipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", desedeCipher.getProperty("paddingType"));
        assertNotNull("test that the returned iv data is correct", desedeCipher.getProperty("iv")); ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), desedeCipher.getProperty("key"));

        // -- AES
        AES128Cipher aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        aes128Cipher.setIv(iv);
        assertEquals("test that the returned key type data is correct", "AES", aes128Cipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", aes128Cipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", aes128Cipher.getProperty("paddingType"));
        assertEquals("test that the returned iv data is correct", new String(Hex.encode(iv)), aes128Cipher.getProperty("iv")) ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), aes128Cipher.getProperty("key"));

        // with random IV
        aes128Cipher = new AES128Cipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        aes128Cipher.useRandomIv();
        assertEquals("test that the returned key type data is correct", "AES", aes128Cipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", aes128Cipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", aes128Cipher.getProperty("paddingType"));
        assertNotNull("test that the returned iv data is correct", aes128Cipher.getProperty("iv")); ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), aes128Cipher.getProperty("key"));

        // -- DES
        DESCipher desCipher = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        desCipher.setIv(iv);
        assertEquals("test that the returned key type data is correct", "DES", desCipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", desCipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", desCipher.getProperty("paddingType"));
        assertEquals("test that the returned iv data is correct", new String(Hex.encode(iv)), desCipher.getProperty("iv")) ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), desCipher.getProperty("key"));

        // with random IV
        desCipher = new DESCipher(encryptionKey, SymmetricCipher.OperatingMode.CBC, SymmetricCipher.PaddingType.PKCS7Padding);
        desCipher.useRandomIv();
        assertEquals("test that the returned key type data is correct", "DES", desCipher.getProperty("keyType")) ;
        assertEquals("test that the returned operating mode data is correct", "CBC", desCipher.getProperty("operatingMode"));
        assertEquals("test that the returned padding type data is correct", "PKCS7Padding", desCipher.getProperty("paddingType"));
        assertNotNull("test that the returned iv data is correct", desCipher.getProperty("iv")); ;
        assertEquals("test that the returned key data is correct", new String(Hex.encode(encryptionKey)), desCipher.getProperty("key"));

    }

    //----------------------------  UTILS ----------------------------

    public Key generateKey(byte[] keyBytes, int noOfBytes, String algorithm) {
        if (null == keyBytes || keyBytes.length < 0) {
            System.out.println("No encryption bytes set. Generating random key");
            SecureRandom random = new SecureRandom() ;
            keyBytes = new byte[noOfBytes] ;
            random.nextBytes(keyBytes);
        }
        SecretKeySpec key = new SecretKeySpec(keyBytes, algorithm) ;
        return key;
    }
}