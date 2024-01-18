package ke.co.nectar.token.domain.table.permutationtable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EncryptingPermutationTableTest {

    private EncryptingPermutationTable encryptingPermutationTable ;

    @Before
    public void setUp() throws Exception {
        encryptingPermutationTable = new EncryptingPermutationTable();
    }

    @Test
    public void testThatPermutationTableValuesAreValid() {
        int validPermutationTableValues [] = {  55, 42, 10, 18, 24, 21, 44, 35, 2, 22, 56, 43, 27,
                                                58, 9, 50, 6, 36, 12, 61, 37, 38, 53, 16, 62, 3, 7, 4,
                                                32, 20, 63, 25, 51, 52, 54, 33, 49, 19, 46, 29, 48,
                                                31, 23, 30, 41, 28, 13, 5, 40, 60, 39, 11, 15, 17,
                                                1, 0, 57, 34, 59, 8, 47, 14, 45, 26 } ;
        boolean permutationTableValid = true ;
        for (int permutationTableValCounter = 0;
             permutationTableValCounter < validPermutationTableValues.length;
             permutationTableValCounter++) {
            int permutationTableVal = validPermutationTableValues[permutationTableValCounter] ;
            if (permutationTableVal != encryptingPermutationTable.getValue(permutationTableValCounter)) {
                permutationTableValid = false ;
                break;
            }
        }
        assertTrue("test that permutation table is valid", permutationTableValid);
    }

}