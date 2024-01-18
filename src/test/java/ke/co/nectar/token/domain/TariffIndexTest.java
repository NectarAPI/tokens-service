package ke.co.nectar.token.domain;

import ke.co.nectar.token.exceptions.InvalidTariffIndexException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TariffIndexTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatTariffIndexPropertiesAreCorrectlyReturned() {
        try {
            final String tariffIndexValue = "01";
            TariffIndex tariffIndex = new TariffIndex(tariffIndexValue);
            assertEquals("test that the tariff index properties returned are correct", "01", tariffIndex.getValue());
            assertEquals("test that the correct tariff index name is returned", "Tariff Index", tariffIndex.getName());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testThatInvalidTariffIndexPropertiesThrowAnException()
        throws InvalidTariffIndexException {
        final String tariffIndexValue = "a1";
        expectedException.expect(InvalidTariffIndexException.class);
        expectedException.expectMessage("Invalid Tariff Index");
        TariffIndex tariffIndex = new TariffIndex(tariffIndexValue);
        assertEquals("test that the tariff index properties returned are correct", "01", tariffIndex.getValue());
        assertEquals("test that the correct tariff index name is returned", "Tariff Index", tariffIndex.getName());
    }

}