package ke.co.nectar.token.generators.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LuhnAlgorithmTest {

    @Test
    public void testThatLuhnAlgorithmGeneratesCorrectCheckDigit() {
        long value = 1234567890;
        assertEquals("test that the correct luhn algorithm is generated", 3, LuhnAlgorithm.generateCheckDigit(value));

        value = 43135625623l;
        assertEquals("test that the correct luhn algorithm is generated", 7, LuhnAlgorithm.generateCheckDigit(value));

        value = 7861754265922l;
        assertEquals("test that the correct luhn algorithm is generated", 0, LuhnAlgorithm.generateCheckDigit(value));
    }
}