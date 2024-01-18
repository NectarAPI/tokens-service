package ke.co.nectar.token.domain.token;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.TokenCarrierType;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1.InitiateMeterTestOrDisplay1TokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1.InitiateMeterTestOrDisplay2TokenGenerator;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import static ke.co.nectar.token.domain.meterprimaryaccountnumber.MeterPrimaryAccountNumber.Validate.NO_METER_PAN_VALIDATION;

public class STSComplianceTests_STS_531_1_0_02_CTSA02 {

    private TokenCarrierType magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
    private TokenCarrierType numericTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.NUMERIC);

    @Test
    public void step1Test() {
        try {
            String requestID = "request_id";
            MeterPrimaryAccountNumber meterPAN = new MeterPrimaryAccountNumber("600727000000000009", NO_METER_PAN_VALIDATION);
            ManufacturerCode manufacturerCode = meterPAN.getIndividualAccountIdentificationNumber().getManufacturerCode();
            Control control = new Control(new BitString("111111111111111111111111111111111111"), manufacturerCode);
            InitiateMeterTestOrDisplay1TokenGenerator generator = new InitiateMeterTestOrDisplay1TokenGenerator(requestID, control, manufacturerCode);
            assertEquals("test that correct token is generated", "56493153725450313471", generator.generate().getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2Test() {
        try {
            MeterPrimaryAccountNumber meterPAN = new MeterPrimaryAccountNumber("000001000000000082", NO_METER_PAN_VALIDATION);
            // ManufacturerCode manufacturerCode = meterPAN.getIndividualAccountIdentificationNumber().getManufacturerCode();
            String requestID = "request_id";
            ManufacturerCode manufacturerCode = new ManufacturerCode("0000");
            Control control = new Control(new BitString("1111111111111111111111111111"), manufacturerCode);
            InitiateMeterTestOrDisplay2TokenGenerator generator = new InitiateMeterTestOrDisplay2TokenGenerator(requestID, control, manufacturerCode);
            assertEquals("test that correct token is generated", "02305843005052951967", generator.generate().getTokenNo());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
