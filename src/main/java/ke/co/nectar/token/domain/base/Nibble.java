package ke.co.nectar.token.domain.base;

import ke.co.nectar.token.exceptions.InvalidNibbleBitStringException;
import ke.co.nectar.token.miscellaneous.Strings;

public class Nibble {

    private BitString bitString = new BitString() ;
    private final int NO_BITS_NIBBLE = 4;
    private final int MAX_NIBBLE_VALUE = 15; // 15 = 0x0F

    public Nibble () {
        bitString.setLength(NO_BITS_NIBBLE);
    }

    public Nibble (BitString bitString)
            throws InvalidNibbleBitStringException {
        setNibble(bitString);
        bitString.setLength(NO_BITS_NIBBLE);
    }

    public BitString getNibble () {
        return bitString ;
    }

    public void setNibble (BitString bitString)
            throws InvalidNibbleBitStringException {
        if (bitString.getValue() <= MAX_NIBBLE_VALUE)
            this.bitString = bitString ;
        else
            throw new InvalidNibbleBitStringException(Strings.ILLEGAL_NIBBLE_BITSTRING) ;
    }

    public char[] getCharArray() {
        return bitString.getBits() ;
    }

}
