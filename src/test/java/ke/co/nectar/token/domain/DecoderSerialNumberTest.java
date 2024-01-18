package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.CharactersNotDigitException;
import ke.co.nectar.token.exceptions.InvalidDecoderSerialNumberException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DecoderSerialNumberTest {

    private DecoderSerialNumber decoderSerialNumber ;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatDecoderSerialNumberReturnedIsCorrect() {
        try {
            String decoderSerialNumberValue = "12345678";
            decoderSerialNumber = new DecoderSerialNumber(decoderSerialNumberValue);
            assertEquals("test that the correct decoder serial number is failed", decoderSerialNumberValue, decoderSerialNumber.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatDecoderSerialNumberWithInvalidLengthThrowsError()
        throws InvalidDecoderSerialNumberException {
        String decoderSerialNumberValue = "1234";
        expectedException.expect(InvalidDecoderSerialNumberException.class);
        expectedException.expectMessage("Invalid decoder serial number");
        decoderSerialNumber = new DecoderSerialNumber(decoderSerialNumberValue);
    }

    @Test
    public void testThatDecoderSerialNumberWithInvalidCharsThrowsError()
            throws InvalidDecoderSerialNumberException, CharactersNotDigitException {
        String decoderSerialNumberValue = "abcd";
        expectedException.expect(InvalidDecoderSerialNumberException.class);
        expectedException.expectMessage("Invalid decoder serial number");
        decoderSerialNumber = new DecoderSerialNumber(decoderSerialNumberValue);
    }

}