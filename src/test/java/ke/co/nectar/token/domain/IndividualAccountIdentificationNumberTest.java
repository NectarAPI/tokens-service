package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidDrnCheckDigitException;
import ke.co.nectar.token.exceptions.InvalidIssuerIAINComponents;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IndividualAccountIdentificationNumberTest {

    private IndividualAccountIdentificationNumber individualAccountIdentificationNumber ;
    private ManufacturerCode manufacturerCode;
    private DecoderSerialNumber decoderSerialNumber;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        manufacturerCode = mock(ManufacturerCode.class);
        decoderSerialNumber = mock(DecoderSerialNumber.class);
    }

    @Test
    public void testThatIndividualAccountIdentificationNumberPropertiesAreReturned() {
        try {
            when(manufacturerCode.getValue()).thenReturn("12");
            when(decoderSerialNumber.getValue()).thenReturn("12345678");
            individualAccountIdentificationNumber = new IndividualAccountIdentificationNumber(manufacturerCode, decoderSerialNumber);
            assertEquals("test that correct manufacturer code is returned", "12", individualAccountIdentificationNumber.getManufacturerCode().getValue());
            assertEquals("test that the correct encryption algorithm serial number is returned", "12345678", individualAccountIdentificationNumber.getDecoderSerialNumber().getValue());
            assertEquals("test that the correct DecoderReferenceNumberCheckDigit is returned", 7, individualAccountIdentificationNumber.getDecoderReferenceNumberCheckDigit().getValue());
            assertEquals("test that the correct name is returned", "Individual Account Identification Number", individualAccountIdentificationNumber.getName());
            assertEquals("test thtat the correct IAIN/DRN is returned", "12123456787", individualAccountIdentificationNumber.getValue());
        } catch (InvalidIssuerIAINComponents | InvalidDrnCheckDigitException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidIndividualAccountIdentificationNumberReturnsAnException()
        throws InvalidIssuerIAINComponents, InvalidDrnCheckDigitException {
        when(manufacturerCode.getValue()).thenReturn("aa12");
        when(decoderSerialNumber.getValue()).thenReturn("82345671");
        thrown.expect(InvalidIssuerIAINComponents.class);
        thrown.expectMessage("Invalid Individual Account Identification Number");
        individualAccountIdentificationNumber = new IndividualAccountIdentificationNumber(manufacturerCode, decoderSerialNumber);
        assertEquals("test that correct manufacturer code is returned", "12", individualAccountIdentificationNumber.getManufacturerCode().getValue());
        assertEquals("test that the correct encryption algorithm serial number is returned", "12345678", individualAccountIdentificationNumber.getDecoderSerialNumber().getValue());
        assertEquals("test that the correct DecoderReferenceNumberCheckDigit is returned", 7, individualAccountIdentificationNumber.getDecoderReferenceNumberCheckDigit().getValue());
    }

}