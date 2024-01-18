package ke.co.nectar.impl;

import ke.co.nectar.hsm.prism.impl.PrismClientFacade;
import ke.co.nectar.hsm.prism.impl.PrismHSMConnector;
import ke.co.nectar.token.domain.*;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.StandardTransferAlgorithmEncryptionAlgorithm;
import ke.co.nectar.token.domain.supplygroupcode.SupplyGroupCode;
import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.domain.token.class2.Class2Token;

import java.util.List;
import java.util.UUID;

class PrismClientFacadeIntegrationTest {

    private String host = "197.156.230.188";
    private int port = 9443;
    private String realm = "local";
    private String username = "thrift_lagos";
    private String password = "z3WAnCHQ1";
    private PrismHSMConnector connector = new PrismHSMConnector();

    public static void main(String[] args) {
        try {
            PrismClientFacadeIntegrationTest test = new PrismClientFacadeIntegrationTest();
            test.generateElectricityToken();
            test.generateWaterToken();
            test.generateGasToken();
            test.generateInitiateMeterTestOrDisplay1PrimaryToken();
            test.generateInitiateMeterTestOrDisplay1TestLoadSwitchToken();
            test.generateInitiateMeterTestOrDisplay1TestInformationDisplayToken();
            test.generateInitiateMeterTestOrDisplay1DisplayKWhEnergyTotalsToken();
            test.generateInitiateMeterTestOrDisplay1DisplayTheKRNToken();
            test.generateInitiateMeterTestOrDisplay1DisplayTheTIToken();
            test.generateInitiateMeterTestOrDisplay1TestTokenReaderDeviceToken();
            test.generateInitiateMeterTestOrDisplay1DisplayMeterPowerLimitToken();
            test.generateInitiateMeterTestOrDisplay1DisplayTamperStatusToken();
            test.generateInitiateMeterTestOrDisplay1DisplayPowerConsumptionToken();
            test.generateInitiateMeterTestOrDisplay1DisplaySoftwareVersionToken();
            test.generateInitiateMeterTestOrDisplay1DisplayPhasePowerUnbalanceLimitToken();
            test.generateInitiateMeterTestOrDisplay1DisplayWaterMeterFactorToken();
            test.generateInitiateMeterTestOrDisplay1DisplayTariffRateToken();
            test.generateInitiateMeterTestOrDisplay1DisplayEAValueToken();
            test.generateInitiateMeterTestOrDisplay1DisplayNumberofKCTsSupportedToken();
            test.generateInitiateMeterTestOrDisplay1DisplaySGCValueToken();
            test.generateInitiateMeterTestOrDisplay1DisplayKENValueToken();
            test.generateInitiateMeterTestOrDisplay1DisplayDRNValueToken();
            test.generateInitiateMeterTestOrDisplay1InitiateMeterTestToken();
            test.generateInitiateMeterTestOrDisplay2PrimaryToken();
            test.generateInitiateMeterTestOrDisplay2TestLoadSwitchToken();
            test.generateInitiateMeterTestOrDisplay2TestInformationDisplayToken();
            test.generateInitiateMeterTestOrDisplay2DisplayKWhEnergyTotalsToken();
            test.generateInitiateMeterTestOrDisplay2DisplayTheKRNToken();
            test.generateInitiateMeterTestOrDisplay2DisplayTheTIToken();
            test.generateInitiateMeterTestOrDisplay2TestTokenReaderDeviceToken();
            test.generateInitiateMeterTestOrDisplay2DisplayMeterPowerLimitToken();
            test.generateInitiateMeterTestOrDisplay2DisplayTamperStatusToken();
            test.generateInitiateMeterTestOrDisplay2DisplayPowerConsumptionToken();
            test.generateInitiateMeterTestOrDisplay2DisplaySoftwareVersionToken();
            test.generateInitiateMeterTestOrDisplay2DisplayPhasePowerUnbalanceLimitToken();
            test.generateInitiateMeterTestOrDisplay2DisplayWaterMeterFactorToken();
            test.generateInitiateMeterTestOrDisplay2DisplayTariffRateToken();
            test.generateInitiateMeterTestOrDisplay2DisplayEAValueToken();
            test.generateInitiateMeterTestOrDisplay2DisplayNumberofKCTsSupportedToken();
            test.generateInitiateMeterTestOrDisplay2DisplaySGCValueToken();
            test.generateInitiateMeterTestOrDisplay2DisplayKENValueToken();
            test.generateInitiateMeterTestOrDisplay2DisplayDRNValueToken();
            test.generateInitiateMeterTestOrDisplay2InitiateMeterTestToken();
            test.generateClearCreditToken();
            test.generateClearTamperConditionToken();
            test.generateDecoderKeyTokens();
            test.generateSetMaximumPhasePowerUnbalanceLimitToken();
            test.generateSetMaximumPowerLimitToken();
            test.generateSetTariffRateToken();
            test.generateSetWaterMeterFactorToken();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void generateElectricityToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateTransferElectricityCreditToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new Amount(10), new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateWaterToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateTransferWaterCreditToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new Amount(10), new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateGasToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateTransferElectricityCreditToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new Amount(10), new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1PrimaryToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000000"), manufacturerCode); // Primary((long) 0)

        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1TestLoadSwitchToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000001"), manufacturerCode); // TestLoadSwitch ((long) 1)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1TestInformationDisplayToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000010"), manufacturerCode); // TestInformationDisplay ((long) 2)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayKWhEnergyTotalsToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000011"), manufacturerCode); // DisplayKWhEnergyTotals ((long) 3)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayTheKRNToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000100"), manufacturerCode); // DisplayKRN ((long) 4)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayTheTIToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000101"), manufacturerCode); // DisplayTI ((long) 5)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1TestTokenReaderDeviceToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000110"), manufacturerCode); // TestTokenReaderDevice ((long) 6)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayMeterPowerLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000000111"), manufacturerCode); // DisplayMeterPowerLimit ((long) 7)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayTamperStatusToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001000"), manufacturerCode); // DisplayTamperStatus ((long) 8)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayPowerConsumptionToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001001"), manufacturerCode); // DisplayPowerConsumption ((long) 9)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplaySoftwareVersionToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001010"), manufacturerCode); // DisplaySoftwareVersion ((long) 10)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayPhasePowerUnbalanceLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001011"), manufacturerCode); // DisplayPhasePowerUnbalanceLimit ((long) 11)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayWaterMeterFactorToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001100"), manufacturerCode); // DisplayWaterMeterFactor ((long) 12)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayTariffRateToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001101"), manufacturerCode); // DisplayTariffRate ((long) 13)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayEAValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001110"), manufacturerCode); // DisplayEAValue ((long) 14)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayNumberofKCTsSupportedToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000001111"), manufacturerCode); // DisplayNumberKCTsSupported ((long) 15)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplaySGCValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000010000"), manufacturerCode); // DisplaySGCValue ((long) 16)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayKENValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000010001"), manufacturerCode); // DisplayKENValue ((long) 17)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1DisplayDRNValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000010010"), manufacturerCode); // DisplayDRNValue ((long) 18)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay1InitiateMeterTestToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("000000000000000000000000000000100000"), manufacturerCode); // InitiateMeterTest ((long) 32)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay1Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2PrimaryToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000000"), manufacturerCode); // Primary((long) 0)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2TestLoadSwitchToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000001"), manufacturerCode); // TestLoadSwitch ((long) 1))

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2TestInformationDisplayToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000010"), manufacturerCode); // TestInformationDisplay ((long) 2)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayKWhEnergyTotalsToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000011"), manufacturerCode); // DisplayKWhEnergyTotals ((long) 3)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayTheKRNToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000100"), manufacturerCode); // DisplayKRN ((long) 4)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayTheTIToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000101"), manufacturerCode); // DisplayTI ((long) 5)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2TestTokenReaderDeviceToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000110"), manufacturerCode); // TestTokenReaderDevice ((long) 6)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayMeterPowerLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000000111"), manufacturerCode); // DisplayMeterPowerLimit ((long) 7)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayTamperStatusToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001000"), manufacturerCode); // DisplayTamperStatus ((long) 8)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayPowerConsumptionToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001001"), manufacturerCode); // DisplayPowerConsumption ((long) 9)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplaySoftwareVersionToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001010"), manufacturerCode); // DisplaySoftwareVersion ((long) 10)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayPhasePowerUnbalanceLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001011"), manufacturerCode); // DisplayPhasePowerUnbalanceLimit ((long) 11)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayWaterMeterFactorToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001100"), manufacturerCode); // DisplayWaterMeterFactor ((long) 12)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayTariffRateToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001101"), manufacturerCode); // DisplayTariffRate ((long) 13)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayEAValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001110"), manufacturerCode); // DisplayEAValue ((long) 14)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayNumberofKCTsSupportedToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000001111"), manufacturerCode); // DisplayNumberKCTsSupported ((long) 15)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplaySGCValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000010000"), manufacturerCode); // DisplaySGCValue ((long) 16)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayKENValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000010001"), manufacturerCode); // DisplayKENValue ((long) 17)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2DisplayDRNValueToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000010010"), manufacturerCode); // DisplayDRNValue ((long) 18)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateInitiateMeterTestOrDisplay2InitiateMeterTestToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("5800008459826");

        ManufacturerCode manufacturerCode = iain.getManufacturerCode();
        Control control = new Control(new BitString("0000000000000000000000100000"), manufacturerCode); // InitiateMeterTest ((long) 32)

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateInitiateMeterTestOrDisplay2Token(messageID, iain,
                                                                        control, manufacturerCode);
        System.out.println(token.getTokenNo());
    }

    private void generateSetMaximumPowerLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        MaximumPowerLimit maximumPowerLimit = new MaximumPowerLimit(10);
        Token token = facade.generateSetMaximumPowerLimitToken(messageID, maximumPowerLimit, PrismHSMConnector.FlagTokenType.SetFlagCTSTest,
                PrismHSMConnector.FlagTokenValue.Enable, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateClearCreditToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");
        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateClearCreditToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateSetTariffRateToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber iain =
                new IndividualAccountIdentificationNumber("58000079523");
        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateSetTariffRateToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateDecoderKeyTokens() throws Exception {
        String messageID = UUID.randomUUID().toString();
        boolean allow3Kct = true;
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        List<Class2Token> tokens = facade.generateDecoderKeyTokens(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new TariffIndex("21"), allow3Kct);
        for (Class2Token token : tokens) {
            System.out.println(token.getTokenNo());
        }
    }

    private void generateClearTamperConditionToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateClearTamperConditionToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateSetMaximumPhasePowerUnbalanceLimitToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");

        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateSetMaximumPhasePowerUnbalanceLimitToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }

    private void generateSetWaterMeterFactorToken() throws Exception {
        String messageID = UUID.randomUUID().toString();
        IndividualAccountIdentificationNumber
                iain = new IndividualAccountIdentificationNumber("58000079523");
        PrismClientFacade facade
                = new PrismClientFacade(host, port, realm, username, password, connector);
        Token token = facade.generateSetWaterMeterFactorToken(messageID, iain, new StandardTransferAlgorithmEncryptionAlgorithm(),
                new TokenCarrierType(TokenCarrierType.Code.NUMERIC), new SupplyGroupCode("600675"), new KeyRevisionNumber(1),
                new KeyExpiryNumber(255), new TariffIndex("21"));
        System.out.println(token.getTokenNo());
    }
}