package ke.co.nectar.token.domain.tokenclass;

import ke.co.nectar.token.domain.Entity;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.miscellaneous.Strings;

public class TokenClass implements Entity {

    private BitString tokenClassBitString ;
    private final int NO_OF_BITS = 2;
    private String name = "Token class" ;

    public TokenClass (long tokenClass, String name)
            throws InvalidRangeException {
        setBitString(tokenClass);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public BitString getBitString() {
        return tokenClassBitString;
    }

    public void setBitString(long tokenClass)
            throws InvalidRangeException {
        if (tokenClass <= 3 &&
            tokenClass >= 0) {
            BitString bitString = new BitString(tokenClass);
            bitString.setLength(NO_OF_BITS);
            this.tokenClassBitString = bitString;
        } else
            throw new InvalidRangeException(Strings.BIT_STRING_SIZE_ERROR) ;
    }

    public String toString () {
        return getBitString().toString() ;
    }

    public String bitsToString() {
        return String.format("%" + NO_OF_BITS + "s", Long.toBinaryString(tokenClassBitString.getValue())).replace(' ', '0');
    }
}
