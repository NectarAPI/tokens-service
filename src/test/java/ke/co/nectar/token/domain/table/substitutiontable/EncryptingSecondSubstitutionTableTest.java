package ke.co.nectar.token.domain.table.substitutiontable;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EncryptingSecondSubstitutionTableTest {

    private EncryptingSecondSubstitutionTable encryptingSecondSubstitutionTable;

    @Before
    public void setUp() throws Exception {
        encryptingSecondSubstitutionTable = new EncryptingSecondSubstitutionTable();
    }

    @Test
    public void testThatSecondSubstitutionTableValuesAreValid() {
        int validFirstSubstitutionTable[] = { 12, 8, 2, 13, 7, 6, 1, 3, 11, 5, 9, 15, 0, 4, 10, 14 };
        boolean permutationTableValid = true;
        for (int permutationTableValCounter = 0;
             permutationTableValCounter < validFirstSubstitutionTable.length;
             permutationTableValCounter++) {
            int permutationTableVal = validFirstSubstitutionTable[permutationTableValCounter];
            if (permutationTableVal != encryptingSecondSubstitutionTable.getValue(permutationTableValCounter)) {
                permutationTableValid = false;
                break;
            }
        }
        assertTrue("test that permutation table is valid", permutationTableValid);
    }

}