package ke.co.nectar.token.domain.base;

import ke.co.nectar.token.exceptions.IllegalComparisonError;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.NibbleOutOfRangeException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BitStringTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatBitStringInstanceIsCreated() throws Exception {
        BitString bitString1 = new BitString() ;
        assertTrue("Test that a bit string instance is created using several modes", bitString1 != null);
        assertTrue("test that the initialized bitset values are all 0", bitString1.getBit(0).getValue() == '0' &&
                bitString1.getBit(1).getValue() == '0' &&
                bitString1.getBit(6).getValue() == '0');
    }

    @Test
    public void testThatBitStringIsSet() throws Exception {
        // first form of setting bits
        BitString bs = new BitString() ;
        bs.setBit(10) ;
        bs.setBit(11) ;
        assertTrue("test that specified bits are set", ((int) bs.getBit(10).getValue()) == '1');
        assertTrue("test that specified bits are set", ((int) bs.getBit(11).getValue()) == '1');

        // test clearing of bits
        bs.clearBit(10) ;
        bs.clearBit(11) ;
        assertTrue("test that specified bits are set", ((int) bs.getBit(10).getValue()) == '0');
        assertTrue("test that specified bits are set", ((int) bs.getBit(11).getValue()) == '0');

        // second form of setting bits
        long testString = 0x11L ;
        BitString bs1 = new BitString(testString) ;
        assertTrue("test that specified bits are set", ((int) bs1.getBit(0).getValue()) == '1');
        assertTrue("test that specified bits are set", ((int) bs1.getBit(4).getValue()) == '1');

        bs1.clearBit(0);
        bs1.clearBit(4);
        assertTrue("test that specified bits are set", ((int) bs1.getBit(0).getValue()) == '0');
        assertTrue("test that specified bits are set", ((int) bs1.getBit(4).getValue()) == '0');

        long afterString = 0 ;
        BitString after = new BitString(afterString) ;
        assertEquals("test that cleared string equals empty one", BitString.SAME, bs1.compareTo(after));
    }

    @Test
    public void testCompareToOnBitStrings() throws Exception {
        final String BITSTRING_NAME = "bit string " ;
        long testBitString = 0x0 ;
        BitString bs1 = new BitString(testBitString) ;
        BitString bs2 = new BitString(testBitString) ;
        assertEquals("test that bit strings are equal", BitString.SAME, bs1.compareTo(bs2));

        // test different lengths
        final int BITSTRING_LENGTH1 = 12 ;
        final int BITSTRING_LENGTH2 = 2 ;
        bs1 = new BitString(testBitString, BITSTRING_LENGTH1) ;
        bs2 = new BitString(testBitString, BITSTRING_LENGTH2) ;
        expectedException.expect(IllegalComparisonError.class);
        expectedException.expectMessage("Bit strings of different lengths being compared");
        bs1.compareTo(bs2);

        // test different names for bitstrings
        bs1 = new BitString(testBitString) ;
        bs2 = new BitString(testBitString) ;
        assertEquals("test that bit strings are equal", BitString.LESS_THAN, bs1.compareTo(bs2));
    }

    @Test
    public void testConcatenationBitSets() throws Exception {
        // first method of setting bits
        final int BITSTRING1_LENGTH = 2 ;
        BitString bst1 = new BitString() ;
        bst1.setLength(BITSTRING1_LENGTH);
        bst1.setBit(0);
        bst1.setBit(1);

        final int BITSTRING2_LENGTH = 3 ;
        BitString bst2 = new BitString() ;
        bst2.setLength(BITSTRING2_LENGTH);
        bst2.setBit(0);
        bst2.setBit(1);
        bst2.setBit(2);

        final int BITSTRING3_LENGTH = 2 ;
        BitString bst3 = new BitString() ;
        bst3.setLength(BITSTRING3_LENGTH);
        bst3.setBit(0);
        bst3.setBit(1);

        final int CONCAT_BITSTRING_LENGTH = 7 ;
        long testBitStringVal = 0x7fL ;
        BitString concat = new BitString(testBitStringVal, CONCAT_BITSTRING_LENGTH) ;

        assertEquals("test concatenation of bits", BitString.SAME, concat.compareTo(bst1.concat(bst2, bst3)));

        // test using second method of setting bits
        final long firstTestString = 0x3L ;
        final int testString1Length = 2 ;
        BitString bs = new BitString(firstTestString, testString1Length) ;

        final long secondTestString = 0x18L ;
        final int testString2Length = 5 ;
        BitString bs1 = new BitString(secondTestString, testString2Length) ;

        final long thirdTestString = 0x80L ;
        final int testString3Length = 8 ;
        BitString bs2 = new BitString(thirdTestString, testString3Length) ;

        final long concatTestString = 0x4063L ;
        final int totalTestStringLength = 15 ;
        BitString concatenated = new BitString(concatTestString, totalTestStringLength) ;

        assertEquals("test second form concatenation of bits", BitString.SAME, concatenated.compareTo(bs.concat(bs1, bs2)));
    }

    @Test
    public void testSettingNibble() throws Exception {
        // test using long strings, no length set
        long testString = 0x10fL ;
        long replacementBitString = 0x6L ;
        BitString bitString = new BitString(testString) ;
        Nibble replacementNibble = new Nibble(new BitString(replacementBitString)) ;
        bitString.setNibble(0, replacementNibble);
        assertEquals("test that nibble has been correctly substituted", 0x106L, bitString.getValue());

        bitString = new BitString(testString) ;
        bitString.setNibble(1, replacementNibble);
        assertEquals("test that nibble has been correctly substituted", 0x16fL, bitString.getValue());

        bitString = new BitString(testString) ;
        bitString.setNibble(2, replacementNibble);
        assertEquals("test that nibble has been correctly substituted", 0x60fL, bitString.getValue());

        // check domain.exceptions
        // check invalid length domain.exceptions
        expectedException.expect(NibbleOutOfRangeException.class);
        expectedException.expectMessage("Desired nibble out of domain.range");
        bitString.setNibble(17, replacementNibble);

        expectedException.expect(NibbleOutOfRangeException.class);
        expectedException.expectMessage("Desired nibble out of domain.range");
        bitString.setNibble(17, replacementNibble);

        // test using long strings, length set
        long testString2 = 0x10fL ;
        long replacementBitString2 = 0x6L ;
        BitString bitString2 = new BitString(testString2) ;
        bitString2.setLength(8);
        Nibble replacementNibble2 = new Nibble(new BitString(replacementBitString2)) ;
        bitString.setNibble(0, replacementNibble2);
        assertEquals("test that nibble has been correctly substituted", 0x106L, bitString2.getValue());

        // check domain.exceptions
        // check invalid length domain.exceptions
        expectedException.expect(NibbleOutOfRangeException.class);
        expectedException.expectMessage("Desired nibble out of domain.range");
        bitString2.setNibble(3, replacementNibble2);
    }

    @Test
    public void getNibble() throws Exception {
        // check nibble position, no length set
        long testString = 0x1eL ;
        BitString bitString = new BitString(testString) ;
        Nibble extractedNibble = bitString.getNibble(0) ;
        assertTrue("check that the expected nibble is extracted",
                '0' == extractedNibble.getNibble().getBit(0).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(1).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(2).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(3).getValue());

        extractedNibble = bitString.getNibble(1) ;
        assertTrue("check that second nibble expected is obtained",
                '1' == extractedNibble.getNibble().getBit(0).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(1).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(2).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(3).getValue());

        // check nibble position, length set
        testString = 0x1eL ;
        bitString = new BitString(testString) ;
        bitString.setLength(8);
        extractedNibble = bitString.getNibble(0) ;
        assertTrue("check that the expected nibble is extracted",
                '0' == extractedNibble.getNibble().getBit(0).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(1).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(2).getValue() &&
                        '1' == extractedNibble.getNibble().getBit(3).getValue());

        extractedNibble = bitString.getNibble(1) ;
        assertTrue("check that second nibble expected is obtained",
                '1' == extractedNibble.getNibble().getBit(0).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(1).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(2).getValue() &&
                        '0' == extractedNibble.getNibble().getBit(3).getValue());

        // check invalid length domain.exceptions
        expectedException.expect(NibbleOutOfRangeException.class);
        expectedException.expectMessage("Desired nibble out of domain.range");
        bitString.getNibble(17) ;

        expectedException.expect(NibbleOutOfRangeException.class);
        expectedException.expectMessage("Desired nibble out of domain.range");
        bitString.getNibble(-1) ;

    }

    @Test
    public void testThatBitStringIsRotated() throws Exception {

        // -- LEFT ROTATION, FULL LENGTH --
        // left rotateRight domain.range bits set
        BitString initial = new BitString() ;
        initial.setBitsRange(1, 3);
        initial = BitString.rotate(initial, BitString.Direction.LEFT, 2) ;

        BitString after = new BitString() ;
        after.setBitsRange(3, 5);

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));

        // left rotateRight long bits set
        long testString = 0x1eL ;
        initial = new BitString(testString) ;
        initial = BitString.rotate(initial, BitString.Direction.LEFT, 4) ;

        long afterTestString = 0x1e0L;
        after = new BitString(afterTestString) ;

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));

        // -- RIGHT ROTATION, FULL LENGTH --
        // right rotateRight domain.range bits set
        initial = new BitString() ;
        initial.setBitsRange(1, 3);
        initial = BitString.rotate(initial, BitString.Direction.RIGHT, 2) ;

        after = new BitString() ;
        after.setBit(0);
        after.setBit(1);
        after.setBit(63);

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));

        // right rotateRight long bits set
        testString = 0x1eL ;
        initial = new BitString(testString) ;
        initial = BitString.rotate(initial, BitString.Direction.RIGHT, 4) ;

        afterTestString = 0xe000000000000001L;
        after = new BitString(afterTestString) ;

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));

        // -- LEFT ROTATION, SET LENGTH --
        // first method of setting bit
        initial = new BitString() ;
        initial.setBitsRange(1, 3);
        initial.setLength(4);
        initial = BitString.rotate(initial, BitString.Direction.LEFT, 3) ;

        after = new BitString() ;
        after.setBitsRange(0, 2);
        after.setLength(4);

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));

        // second way of setting bits (using long values)
        testString = 0x1eL ;
        initial = new BitString(testString) ;
        initial.setLength(5);
        initial = BitString.rotate(initial, BitString.Direction.RIGHT, 4) ;

        after = new BitString() ;
        after.setBit(0) ;
        after.setBit(2);
        after.setBit(3);
        after.setBit(4);
        after.setLength(5) ;

        assertEquals("test rotation of bit string to right", BitString.SAME, initial.compareTo(after));
    }

    @Test
    public void testThatBitRangeIsCleared()
            throws InvalidRangeException {

        // test with no length set
        long testString = 0x1fL ;
        BitString initial = new BitString(testString) ;
        initial.clearBitRange(0, 3);

        testString = 0x10L ;
        BitString after = new BitString(testString) ;

        assertEquals("test that the required bit domain.range is cleared", BitString.SAME, initial.compareTo(after));

        // test with length set
        testString = 0x1fL ;
        initial = new BitString(testString) ;
        initial.setLength(5);
        initial.clearBitRange(0, 3);

        testString = 0x10L ;
        after = new BitString(testString) ;
        after.setLength(5);

        assertEquals("test that the required bit domain.range is cleared", BitString.SAME, initial.compareTo(after));
    }
}