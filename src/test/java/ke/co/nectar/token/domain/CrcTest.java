package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrcTest {

    private Crc crc ;

    @Before
    public void setUp() {
        crc = new Crc() ;
    }

    @After
    public void tearDown() {
        crc = null ;
    }

    @Test
    public void testThatCorrectBitStringIsSet() {
        try {
            final int NO_OF_BITS = 16 ;
            BitString testCrcBitString = new BitString(0x1234L);
            testCrcBitString.setLength(NO_OF_BITS);
            crc.setCrcBitString(testCrcBitString);
            assertEquals("test that the inserted bitstring is returned", testCrcBitString, crc.getBitString());
        } catch (InvalidRangeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatCorrectNameForElementIsReturned() {
        assertTrue("test that the correct Crc name is returned", crc.getName().equals("Crc"));
    }

    @Test
    public void testThatCorrectCrcIsGenerated() {
        final long BITSTRING_FROM_STANDARD = 12205947027712l;
        final long CRC_FROM_STANDARD = 49671l ;
        BitString bitstringUsedToGenerateCrc = new BitString(BITSTRING_FROM_STANDARD) ;
        BitString expectedCrc = new BitString(CRC_FROM_STANDARD) ;
        assertEquals("test that the generated Crc is correct", expectedCrc.getValue(), crc.generateCRC(bitstringUsedToGenerateCrc).getValue());
    }

    @Test
    public void testThatCorrectCrcBitStringIsGenerated() {
        try {
            final int NO_OF_BITS = 16;
            BitString testCrcBitString = new BitString(0x4321L);
            testCrcBitString.setLength(NO_OF_BITS);
            crc.setCrcBitString(testCrcBitString);
            assertEquals("test that the correct bitstring sequence is returned", "0100001100100001", crc.bitsToString());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}