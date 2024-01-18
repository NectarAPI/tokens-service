package ke.co.nectar.token.domain.token.class2;

import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.rate.InvalidRateException;
import ke.co.nectar.token.domain.rate.Rate;
import ke.co.nectar.token.domain.tokenclass.class2.ClearCreditTokenClass;
import ke.co.nectar.token.domain.tokenclass.class2.SetTariffRateTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class2.ClearCreditTokenSubClass;
import ke.co.nectar.token.domain.tokensubclass.class2.SetTariffRateTokenSubClass;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;
import ke.co.nectar.token.exceptions.InvalidDateTimeBitsException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.generators.tokensdecoder.error.CRCError;

import java.util.HashMap;
import java.util.Optional;

public class SetTariffRateToken extends Class2Token {

    private Optional<RandomNo> randomNo;
    private TokenIdentifier tokenIdentifier;
    private Optional<Rate> rate;

    public SetTariffRateToken(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new SetTariffRateTokenClass());
        setTokenSubClass(new SetTariffRateTokenSubClass());
    }

    public SetTariffRateToken(String requestID,
                              Optional<RandomNo> randomNo,
                              TokenIdentifier tokenIdentifier,
                              Optional<Rate> rate)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new SetTariffRateTokenClass());
        setTokenSubClass(new SetTariffRateTokenSubClass());
        setRandomNo(randomNo);
        setTokenIdentifier(tokenIdentifier);
        setRate(rate);
    }

    @Override
    public String getType() {
        return "SetTariffRate_22";
    }

    public String getBitString() {
        return encryptedTokenBitString;
    }

    public Optional<RandomNo> getRandomNo() {
        return randomNo;
    }

    public void setRandomNo(Optional<RandomNo> randomNo) {
        this.randomNo = randomNo;
    }

    public TokenIdentifier getTokenIdentifier() {
        return tokenIdentifier;
    }

    public void setTokenIdentifier(TokenIdentifier tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier;
    }

    public Optional<Rate> getRate() {
        return rate;
    }

    public void setRate(Optional<Rate> rate) {
        this.rate = rate;
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("class", getTokenClass().getBitString().getValue());
        params.put("subclass", getTokenSubClass().getBitString().getValue());
        params.put("token_identifier", getTokenIdentifier().getTimeOfIssue().toString());
        params.put("type", getType());

        if (getRate().isPresent())
            params.put("tariff_rate", getRate().get().getRateBitString().getValue());

        if (getRandomNo().isPresent())
            params.put("rnd", getRandomNo().get().getBitString().getValue());

        return params;
    }

    public void decode(BitString decryptedTokenBitString,
                       BitString encryptedTokenBitString) throws
            CRCError,
            InvalidRangeException,
            InvalidDateTimeBitsException,
            InvalidRateException, BitConcatOverflowError {
        if (checkCrc(decryptedTokenBitString, getTokenClass())) {
            setRandomNo(extractRandomNo(decryptedTokenBitString));
            setTokenIdentifier(extractTokenIdentifier(decryptedTokenBitString));
            setRate(Optional.of(new Rate(decryptedTokenBitString.extractBits(16, 16))));
            setEncryptedTokenBitString(Long.toBinaryString(encryptedTokenBitString.getValue()));
            setDecryptedTokenBitString(Long.toBinaryString(decryptedTokenBitString.getValue()));
        }
    }
}
