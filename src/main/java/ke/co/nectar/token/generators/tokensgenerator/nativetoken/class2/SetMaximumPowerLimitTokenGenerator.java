package ke.co.nectar.token.generators.tokensgenerator.nativetoken.class2;

import ke.co.nectar.token.domain.Crc;
import ke.co.nectar.token.domain.MaximumPowerLimit;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.encryptionalgorithm.EncryptionAlgorithm;
import ke.co.nectar.token.domain.keys.decoder.DecoderKey;
import ke.co.nectar.token.domain.token.class2.SetMaximumPowerLimitToken;
import ke.co.nectar.token.domain.tokenclass.class2.SetMaximumPowerLimitTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class2.SetMaximumPowerLimitTokenSubClass;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;
import ke.co.nectar.token.exceptions.InvalidBitStringException;
import ke.co.nectar.token.exceptions.InvalidRangeException;

import java.util.Optional;

public class SetMaximumPowerLimitTokenGenerator extends Class2TokenGenerator<SetMaximumPowerLimitToken> {

    private RandomNo randomNo;
    private TokenIdentifier tokenIdentifier;
    private MaximumPowerLimit maximumPowerLimit;

    public SetMaximumPowerLimitTokenGenerator(String requestID,
                                                RandomNo randomNo,
                                                TokenIdentifier tokenIdentifier,
                                                MaximumPowerLimit maximumPowerLimit,
                                                DecoderKey decoderKey,
                                                EncryptionAlgorithm encryptionAlgorithm) {
        super(requestID);
        setRandomNo(randomNo);
        setTokenIdentifier(tokenIdentifier);
        setMaximumPowerLimit(maximumPowerLimit);
        setDecoderKey(decoderKey);
        setEncryptionAlgorithm(encryptionAlgorithm);
    }

    public SetMaximumPowerLimitToken generate() throws Exception {
        BitString concat = maximumPowerLimit.getBitString().concat(tokenIdentifier.getBitString(),
                                                                                    randomNo.getBitString(),
                                                                                    getTokenSubClass().getBitString(),
                                                                                    getTokenClass().getBitString());
        BitString generatedCrcBitString = new Crc().generateCRC(concat);
        Crc generatedCrc = new Crc(generatedCrcBitString);
        SetMaximumPowerLimitToken token = new SetMaximumPowerLimitToken(requestID, Optional.of(randomNo), tokenIdentifier, maximumPowerLimit, Optional.of(generatedCrc));
        BitString _64BitString =  generatedCrcBitString.concat( maximumPowerLimit.getBitString(),
                                                                tokenIdentifier.getBitString(),
                                                                randomNo.getBitString(),
                                                                getTokenSubClass().getBitString());
        String encryptedToken = encrypt(getTokenClass(), _64BitString, getEncryptionAlgorithm());
        token.setEncryptedTokenBitString(encryptedToken);
        return token;
    }

    public BitString generate64BitDataBlock(SetMaximumPowerLimitToken token)
            throws BitConcatOverflowError, InvalidBitStringException, InvalidRangeException {
        BitString tokenClass = token.getTokenClass().getBitString();
        BitString tokenSubClass = token.getTokenSubClass().getBitString();
        BitString rnd = token.getRandomNo().get().getBitString();
        BitString tid = token.getTokenIdentifier().getBitString();
        BitString mpl = token.getMaximumPowerLimit().getBitString();
        BitString concatenated = mpl.concat(tid, rnd, tokenSubClass, tokenClass);
        BitString crc = new Crc().generateCRC(concatenated);
        BitString _64BitDataBlock = crc.concat(mpl, tid, rnd, tokenSubClass);
        return _64BitDataBlock;
    }

    public SetMaximumPowerLimitTokenClass getTokenClass() throws InvalidRangeException {
        return new SetMaximumPowerLimitTokenClass();
    }

    public SetMaximumPowerLimitTokenSubClass getTokenSubClass() throws InvalidRangeException {
        return new SetMaximumPowerLimitTokenSubClass();
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

    public MaximumPowerLimit getMaximumPowerLimit() {
        return maximumPowerLimit;
    }

    public void setMaximumPowerLimit(MaximumPowerLimit maximumPowerLimit) {
        this.maximumPowerLimit = maximumPowerLimit;
    }
}
