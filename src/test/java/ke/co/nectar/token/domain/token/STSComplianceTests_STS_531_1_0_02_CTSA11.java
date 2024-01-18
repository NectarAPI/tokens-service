package ke.co.nectar.token.domain.token;

import ke.co.nectar.token.domain.Control;
import ke.co.nectar.token.domain.ManufacturerCode;
import ke.co.nectar.token.domain.TokenCarrierType;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay1Token;
import ke.co.nectar.token.domain.token.class1.InitiateMeterTestOrDisplay2Token;
import ke.co.nectar.token.exceptions.InvalidControlBitStringException;
import ke.co.nectar.token.exceptions.InvalidManufacturerCodeException;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1.InitiateMeterTestOrDisplay1TokenGenerator;
import ke.co.nectar.token.generators.tokensgenerator.nativetoken.class1.InitiateMeterTestOrDisplay2TokenGenerator;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class STSComplianceTests_STS_531_1_0_02_CTSA11 {

    private TokenCarrierType magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
    private TokenCarrierType virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);
    private ManufacturerCode twoDigitManufacturerCode;
    private ManufacturerCode fourDigitManufacturerCode;
    private Control twoDigitManufactureCodeControl;
    private Control fourDigitManufactureCodeControl;

    private final String REQUEST_ID = "request_id";

    @Before
    public void setUp() {
        try {
            magneticCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.MAGNETIC_CARD);
            virtualCardTokenCarrierType = new TokenCarrierType(TokenCarrierType.Code.VIRTUAL_TOKEN_CARRIER);

            BitString twoDigitManufactureCodeBitString = new BitString(0);
            twoDigitManufactureCodeBitString.setLength(8);
            twoDigitManufacturerCode = new ManufacturerCode(twoDigitManufactureCodeBitString);

            BitString fourDigitManufactureCodeBitString = new BitString(0);
            fourDigitManufactureCodeBitString.setLength(16);
            fourDigitManufacturerCode = new ManufacturerCode(fourDigitManufactureCodeBitString);

        } catch (InvalidManufacturerCodeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step1CTSA10Test() {
        try {
            initializeControlBlocks(0x1l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000000150997584", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921509036054672", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step2CTSA10Test() {
        try {
            initializeControlBlocks(0x2l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000000167774880", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921513331042448", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    public void step3CTSA10Test() {
        try {
            initializeControlBlocks(0x4l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000000201328896", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921521920952465", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step4CTSA10Test() {
        try {
            initializeControlBlocks(0x8l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "18446744073843772416", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921539100838034", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step5CTSA10Test() {
        try {
            initializeControlBlocks(0x10l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "36893488147553322496", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921573460543637", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step6CTSA10Test() {
        try {
            initializeControlBlocks(0x20l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000000671093248", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921642180020378", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step7CTSA10Test() {
        try {
            initializeControlBlocks(0x40l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000001207974400", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152921779618973828", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step8CTSA10Test() {
        try {
            initializeControlBlocks(0x80l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000002281728512", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152922054496880824", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step9CTSA10Test() {
        try {
            initializeControlBlocks(0x100l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000004429208064", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152922604252694700", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step10CTSA10Test() {
        try {
            initializeControlBlocks(0x200l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000008724195840", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152923703764322536", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step11CTSA10Test() {
        try {
            initializeControlBlocks(0x400l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000017314105857", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152925902787577952", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step12CTSA10Test() {
        try {
            initializeControlBlocks(0x2000l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000137573173770", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152956689113154192", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step13CTSA10Test() {
        try {
            initializeControlBlocks(0x4000l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000275012127252", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01152991873485249680", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step14CTSA10Test() {
        try {
            initializeControlBlocks(0x8000l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000000549890034216", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01153062242229428368", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step15CTSA10Test() {
        try {
            initializeControlBlocks(0x10000l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000001099645848124", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01153202979717788816", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void step16CTSA10Test() {
        try {
            initializeControlBlocks(0x20000l);
            InitiateMeterTestOrDisplay1TokenGenerator twoDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay1TokenGenerator(REQUEST_ID, twoDigitManufactureCodeControl, twoDigitManufacturerCode);
            InitiateMeterTestOrDisplay1Token twoDigitManufactureCodeToken = twoDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for two digit manufacturer code", "00000002199157475960", twoDigitManufactureCodeToken.getTokenNo());

            InitiateMeterTestOrDisplay2TokenGenerator fourDigitManufactureCodeGenerator = new InitiateMeterTestOrDisplay2TokenGenerator(REQUEST_ID, fourDigitManufactureCodeControl, fourDigitManufacturerCode);
            InitiateMeterTestOrDisplay2Token fourDigitManufactureCodeToken = fourDigitManufactureCodeGenerator.generate();
            assertEquals("test that the correct InitiateMeterTest/Display token is generated for four digit manufacturer code", "01153484454694514832", fourDigitManufactureCodeToken.getTokenNo());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    private void initializeControlBlocks(long control) {
        try {
            BitString twoDigitManufactureCodeControlBitString = new BitString(control);
            twoDigitManufactureCodeControlBitString.setLength(36);
            twoDigitManufactureCodeControl = new Control(twoDigitManufactureCodeControlBitString, twoDigitManufacturerCode);

            BitString fourDigitManufactureCodeControlBitString = new BitString(control);
            fourDigitManufactureCodeControlBitString.setLength(28);
            fourDigitManufactureCodeControl = new Control(fourDigitManufactureCodeControlBitString, fourDigitManufacturerCode);

        } catch (InvalidControlBitStringException e) {
            e.printStackTrace();
            fail();
        }
    }
}
