package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2;

import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.rate.Rate;
import ke.co.nectar.token.domain.token.class2.SetTariffRateToken;
import ke.co.nectar.token.domain.tokenclass.class2.SetTariffRateTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class2.SetTariffRateTokenSubClass;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;
import ke.co.nectar.token.exceptions.InvalidRangeException;

import java.util.Optional;

public class SetTariffRateTokenGenerator extends Class2TokenGenerator<SetTariffRateToken> {

    private RandomNo randomNo;
    private TokenIdentifier tokenIdentifier;
    private Rate rate;

    public SetTariffRateTokenGenerator(String requestID,
                                       RandomNo randomNo,
                                       TokenIdentifier tokenIdentifier,
                                       Rate rate,
                                       DecoderKey decoderKey,
                                       EncryptionAlgorithm encryptionAlgorithm) {
        super(requestID);
        setRandomNo(randomNo);
        setTokenIdentifier(tokenIdentifier);
        setRate(rate);
        setDecoderKey(decoderKey);
        setEncryptionAlgorithm(encryptionAlgorithm);
    }

    public SetTariffRateToken generate() throws Exception {
        SetTariffRateToken token = new SetTariffRateToken(requestID, Optional.of(randomNo), tokenIdentifier, Optional.of(rate));
        BitString _64BitDataBlock = generate64BitDataBlock(token);
        BitString _64bitStringEncryptedBitString = encryptionAlgorithm.encrypt(getDecoderKey(), _64BitDataBlock);
        String _66bitStringEncryptedBlockAT = transpose66BitString(token.getTokenClass(), _64bitStringEncryptedBitString);
        token.setEncryptedTokenBitString(_66bitStringEncryptedBlockAT);
        return token;
    }

    public BitString generate64BitDataBlock(SetTariffRateToken token) throws BitConcatOverflowError {
        BitString tokenClass = token.getTokenClass().getBitString();
        BitString tokenSubClass = token.getTokenSubClass().getBitString();
        BitString rnd = token.getRandomNo().get().getBitString();
        BitString tid = token.getTokenIdentifier().getBitString();
        BitString rate = token.getRate().get().getRateBitString();
        Crc calcCRC = new Crc();
        BitString crc = calcCRC.generateCRC(rate.concat(tid, rnd, tokenSubClass, tokenClass));
        BitString _64BitDataBlock = crc.concat(rate, tid, rnd, tokenSubClass);
        return _64BitDataBlock;
    }

    public SetTariffRateTokenClass getTokenClass() throws InvalidRangeException {
        return new SetTariffRateTokenClass();
    }

    public SetTariffRateTokenSubClass getTokenSubClass() throws InvalidRangeException {
        return new SetTariffRateTokenSubClass();
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

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
