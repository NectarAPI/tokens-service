package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class DecoderKeyTest {

    private DecoderKey decoderKey ;

    @Before
    public void setUp() {
        decoderKey = new DecoderKey() ;
    }

    @After
    public void tearDown() {
        decoderKey = null ;
    }

    @Test
    public void testThatCorrectDecoderNameIsReturned() {
        assertEquals("test that the correct encryption algorithm keys is returned",
                "Decoder Key", decoderKey.getName());
    }

    @Test
    public void testComplimentingOfDecoderKeyBytes()
    throws IOException  {
        byte[] ec =  { -119, 103, 69, -13, -34, 18, -68, 10 } ;
        byte[] complemented = { 118, -104, -70, 12, 33, -19, 67, -11 } ;
        assertEquals("test that bytes are complemented", base64Encode(complemented),
                base64Encode(decoderKey.complement(ec)));
    }

    @Test
    public void testThatRotationOfDecoderKeyBytes() throws IOException {
        byte[] ec = { 118, -104, -70, 12, 33, -19, 67, -11 } ;
        byte[] rotated = { -87, -53, 16, -46, 62, 84, 111, -121 };
        assertEquals("test that bytes are rotated", base64Encode(rotated),
                base64Encode(decoderKey.rotateComplemented(ec)));
    }

    @Test
    public void testThatCorrectSecretKeyIsSet() throws IOException {
        byte[] secretKeyData = { 0x12, 0x21 } ;
        decoderKey.setKeyData(secretKeyData);
        assertEquals("test that correct bit is returned", base64Encode(secretKeyData),
                base64Encode(decoderKey.getKeyData()));
    }

    @Test
    public void testThatCorrectBitStringIsReturned() {
        byte[] secretKeyData = { 0x12, 0x21 } ;
        decoderKey.setKeyData(secretKeyData);
        assertEquals("test that the correct encryption algorithm keys is represented",
                "0010000100010010", decoderKey.bitsToString());
    }

    @Test
    public void testRotateRightOfDecoderBits() throws IOException {
        int rotatedSteps = 19;
        byte[] secretKeyData = { -87, -53, 16, -46, 62, 84, 111, -121 };
        byte[] rotated = { 66, -38, -121, -22, -19, 48, 117, 25 };
        decoderKey.setKeyData(secretKeyData);
        assertEquals("test that bytes are rotated", base64Encode(rotated),
                base64Encode(decoderKey.rotateRight(secretKeyData, rotatedSteps)));
    }

    @Test
    public void testLeftRotationOfDecoderBits() throws IOException {
        int rotatedSteps = 31;
        byte[] secretKeyData = { -87, -53, 16, -46, 62, 84, 111, -121 };
        byte[] rotated = { 31, -86, -73, -61, -44, 101, 8, 105 };
        decoderKey.setKeyData(secretKeyData);
        assertEquals("test that bytes are rotated", base64Encode(rotated),
                base64Encode(decoderKey.rotateLeft(decoderKey.getKeyData(), rotatedSteps)));
    }

    public String base64Encode(byte[] b) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream(b.length * 2);
        new Base64Encoder().encode(b, 0, b.length, bout);
        return bout.toString(StandardCharsets.UTF_8);
    }
}