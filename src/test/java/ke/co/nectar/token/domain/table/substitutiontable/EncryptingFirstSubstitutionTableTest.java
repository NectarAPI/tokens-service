package ke.co.nectar.token.domain.table.substitutiontable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EncryptingFirstSubstitutionTableTest {

    private EncryptingFirstSubstitutionTable encryptingFirstSubstitutionTable;

    @Before
    public void setUp() throws Exception {
        encryptingFirstSubstitutionTable = new EncryptingFirstSubstitutionTable();
    }

    @Test
    public void testThatSubstitutationTableValuesAreValid() {
        int validFirstSubstitutionTable [] = { 14, 10, 7, 9, 12, 3, 2, 5, 13, 0, 15, 1, 4, 8, 6, 11 } ;
        boolean permutationTableValid = true ;
        for (int permutationTableValCounter = 0;
             permutationTableValCounter < validFirstSubstitutionTable.length;
             permutationTableValCounter++) {
            int permutationTableVal = validFirstSubstitutionTable[permutationTableValCounter] ;
            if (permutationTableVal != encryptingFirstSubstitutionTable.getValue(permutationTableValCounter)) {
                permutationTableValid = false ;
                break;
            }
        }
        assertTrue("test that permutation table is valid", permutationTableValid);
    }
}