package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidKeyTypeException;
import ke.co.nectar.token.exceptions.InvalidPrimaryAccountNumberBlockComponentsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrimaryAccountNumberBlockTest {

    private PrimaryAccountNumberBlock primaryAccountNumberBlock;
    private IssuerIdentificationNumber issuerIdentificationNumber;
    private IndividualAccountIdentificationNumber individualAccountIdentificationNumber;
    private KeyType keyType;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        issuerIdentificationNumber = mock(IssuerIdentificationNumber.class);
        individualAccountIdentificationNumber = mock(IndividualAccountIdentificationNumber.class);
    }

    @Test
    public void tesThatPrimaryAccountNumberBlockIsGenerated() {
        try {

            // first scenario type, primary account number, DDTK
            KeyType keyType = new KeyType(1);
            when(issuerIdentificationNumber.getValue()).thenReturn("123456");
            when(individualAccountIdentificationNumber.getValue()).thenReturn("12345678102");
            primaryAccountNumberBlock = new PrimaryAccountNumberBlock(issuerIdentificationNumber, individualAccountIdentificationNumber, keyType);
            assertEquals("test that the correct primary account number block is generated", "2345612345678102", primaryAccountNumberBlock.getValue());

            // second scenario type, 4 issuer identification number, DUTK
            keyType = new KeyType(2);
            when(issuerIdentificationNumber.getValue()).thenReturn("3456");
            when(individualAccountIdentificationNumber.getValue()).thenReturn("1234567810234");
            primaryAccountNumberBlock = new PrimaryAccountNumberBlock(issuerIdentificationNumber, individualAccountIdentificationNumber, keyType);
            assertEquals("test that the correct primary account number block is generated", "4561234567810234", primaryAccountNumberBlock.getValue());

            // DCTK
            keyType = new KeyType(3);
            when(issuerIdentificationNumber.getValue()).thenReturn("123456");
            when(individualAccountIdentificationNumber.getValue()).thenReturn("12345678102");
            primaryAccountNumberBlock = new PrimaryAccountNumberBlock(issuerIdentificationNumber, individualAccountIdentificationNumber, keyType);
            assertEquals("test that the correct primary account number block is generated", "2345600000000000", primaryAccountNumberBlock.getValue());

            keyType = new KeyType(3);
            when(issuerIdentificationNumber.getValue()).thenReturn("3456");
            when(individualAccountIdentificationNumber.getValue()).thenReturn("12345678102");
            primaryAccountNumberBlock = new PrimaryAccountNumberBlock(issuerIdentificationNumber, individualAccountIdentificationNumber, keyType);
            assertEquals("test that the correct primary account number block is generated", "4560000000000000", primaryAccountNumberBlock.getValue());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidPrimaryAccountNumberBlockValuesThrowException()
        throws InvalidPrimaryAccountNumberBlockComponentsException, InvalidKeyTypeException {
        keyType = new KeyType(3);
        when(issuerIdentificationNumber.getValue()).thenReturn("126");
        when(individualAccountIdentificationNumber.getValue()).thenReturn("1234102");
        expectedException.expect(InvalidPrimaryAccountNumberBlockComponentsException.class);
        expectedException.expectMessage("Invalid Primary Account Number Block Parameters");
        primaryAccountNumberBlock = new PrimaryAccountNumberBlock(issuerIdentificationNumber, individualAccountIdentificationNumber, keyType);
        String panBlock = primaryAccountNumberBlock.getValue();
    }
}