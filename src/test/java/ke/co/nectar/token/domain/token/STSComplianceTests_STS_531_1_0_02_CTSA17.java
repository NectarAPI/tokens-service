package ke.co.nectar.token.domain.token;

import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.exceptions.InvalidIAINNumberException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class STSComplianceTests_STS_531_1_0_02_CTSA17 {

    private MeterPrimaryAccountNumber meterPrimaryAccountNumber;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void step1CTSA17Test() throws Exception {
        expectedException.expect(InvalidIAINNumberException.class);
        expectedException.expectMessage("Invalid Individual Account Identification Number");
        meterPrimaryAccountNumber = new MeterPrimaryAccountNumber("1234567890411111111113");
    }
}
