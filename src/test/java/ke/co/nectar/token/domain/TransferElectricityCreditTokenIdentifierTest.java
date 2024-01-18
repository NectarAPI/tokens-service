package ke.co.nectar.token.domain;

import ke.co.nectar.token.domain.base.BitString;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TransferElectricityCreditTokenIdentifierTest {

    private TokenIdentifier tokenIdentifier;

    @Test
    public void testThatCorrectTIDPropertiesAreCorrectlySet() {
        DateTime now = DateTime.now() ;
        tokenIdentifier = new TokenIdentifier(now, BaseDate._1993) ;
        assertEquals("test that the TokenIdentifier time is valid", now, tokenIdentifier.getTimeOfIssue());
        assertEquals("test that the correct base date is set", new DateTime(1993, 1, 1, 0, 0 ,0), tokenIdentifier.getRefBaseTime());
        assertEquals("test that the correct name is returned", "TokenIdentifier", tokenIdentifier.getName());
    }

    @Test
    public void testThatCorrectTIDIsGenerated() {
        String dateTime = "25/03/1996 13:55:22" ;
        DateTime dateOfIssue = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss").parseDateTime(dateTime);
        tokenIdentifier = new TokenIdentifier(dateOfIssue, BaseDate._1993) ;
        assertEquals("test that the TokenIdentifier generated is valid", "000110011110101100100011", tokenIdentifier.bitsToString());
    }

    @Test
    public void getGenerationDateFromTID() {
        try {
            final int NO_OF_BITS = 24 ;
            BitString tidBitString = new BitString(0x19eb23l);
            tidBitString.setLength(NO_OF_BITS);
            tokenIdentifier = new TokenIdentifier(BaseDate._1993);
            assertEquals("test that the generated date from bitstring is correct", new DateTime(1996, 3, 25, 13, 55, 00), tokenIdentifier.getDateTimeOfIssue(tidBitString));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}