package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidDrnCheckDigitException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DecoderReferenceNumberCheckDigitTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatCorrectDrnCheckDigitIsGenerated() {
        try {
            DecoderReferenceNumberCheckDigit decoderReferenceNumberCheckDigit = new DecoderReferenceNumberCheckDigit(1);
            assertEquals("test that a correct drn check digit is generated", 1, decoderReferenceNumberCheckDigit.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatIncorrectDrnCheckDigitGeneratesError()
        throws InvalidDrnCheckDigitException {
        expectedException.expectMessage("Invalid DRN check digit");
        expectedException.expect(InvalidDrnCheckDigitException.class);
        DecoderReferenceNumberCheckDigit decoderReferenceNumberCheckDigit = new DecoderReferenceNumberCheckDigit(12);
    }

}