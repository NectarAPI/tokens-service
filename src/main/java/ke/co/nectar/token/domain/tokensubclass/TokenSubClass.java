package ke.co.nectar.token.domain.tokensubclass;

import ke.co.nectar.token.domain.Entity;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.miscellaneous.Strings;

public class TokenSubClass implements Entity {

    private BitString tokenSubclassBitString ;
    private final int NO_OF_BITS = 4;
    private String name = "Token SubClass" ;

    public TokenSubClass (long tokenSubClass, String name)
            throws InvalidRangeException {
        setBitString(tokenSubClass);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name ;
    }

    public BitString getBitString() {
        return tokenSubclassBitString;
    }

    public void setBitString(long tokenSubClass)
            throws InvalidRangeException {
        if (tokenSubClass <= 15 &&
            tokenSubClass >= 0) {
            BitString bitString = new BitString(tokenSubClass);
            bitString.setLength(NO_OF_BITS);
            this.tokenSubclassBitString = bitString;
        } else
            throw new InvalidRangeException(Strings.BIT_STRING_SIZE_ERROR) ;
    }

    public String toString () {
        return getBitString().toString() ;
    }

    public String bitsToString() {
        return String.format("%" + NO_OF_BITS + "s", Long.toBinaryString(tokenSubclassBitString.getValue())).replace(' ', '0');
    }
}