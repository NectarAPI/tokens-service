package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2;


import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.Register;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.token.class2.ClearCreditToken;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;

import java.util.Optional;

public class ClearCreditTokenGenerator extends Class2TokenGenerator<ClearCreditToken> {

    private RandomNo randomNo;
    private TokenIdentifier tokenIdentifier;
    private Register register;
    private DecoderKey decoderKey;
    private EncryptionAlgorithm encryptionAlgorithm;

    public ClearCreditTokenGenerator(String requestID,
                                     RandomNo randomNo,
                                     TokenIdentifier tokenIdentifier,
                                     Register register,
                                     DecoderKey decoderKey,
                                     EncryptionAlgorithm encryptionAlgorithm) {
        super(requestID);
        setRandomNo(randomNo);
        setTokenIdentifier(tokenIdentifier);
        setRegister(register);
        setDecoderKey(decoderKey);
        setEncryptionAlgorithm(encryptionAlgorithm);
    }

    public ClearCreditToken generate() throws Exception {
        ClearCreditToken token = new ClearCreditToken(requestID, Optional.of(randomNo), tokenIdentifier, Optional.of(register));
        BitString _64BitDataBlock = generate64BitDataBlock(token);
        BitString _64bitStringEncryptedBitString = encryptionAlgorithm.encrypt(getDecoderKey(), _64BitDataBlock);
        String _66bitStringEncryptedBlockAT = transpose66BitString(token.getTokenClass(), _64bitStringEncryptedBitString);
        token.setEncryptedTokenBitString(_66bitStringEncryptedBlockAT);
        return token;
    }

    public BitString generate64BitDataBlock(ClearCreditToken token) throws BitConcatOverflowError {
        BitString tokenClass = token.getTokenClass().getBitString();
        BitString tokenSubClass = token.getTokenSubClass().getBitString();
        BitString random = token.getRandomNo().get().getBitString();
        BitString tid = token.getTokenIdentifier().getBitString();
        BitString reg = token.getRegister().get().getBitString();
        Crc calcCRC = new Crc();
        BitString crc = calcCRC.generateCRC(reg.concat(tid, random, tokenSubClass, tokenClass));
        BitString _64BitDataBlock = crc.concat(reg, tid, random, tokenSubClass);
        return _64BitDataBlock;
    }

    public RandomNo getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(RandomNo randomNo) {
        this.randomNo = randomNo;
    }

    public TokenIdentifier getTokenIdentifier() {
        return tokenIdentifier;
    }

    public void setTokenIdentifier(TokenIdentifier tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public DecoderKey getDecoderKey() {
        return decoderKey;
    }

    public void setDecoderKey(DecoderKey decoderKey) {
        this.decoderKey = decoderKey;
    }

    public EncryptionAlgorithm getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public void setEncryptionAlgorithm(EncryptionAlgorithm encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }
}
