package ke.co.nectar.token.domain.token.class2;

import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.Register;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.tokenclass.class2.ClearCreditTokenClass;
import ke.co.nectar.token.domain.tokensubclass.class2.ClearCreditTokenSubClass;
import ke.co.nectar.token.exceptions.BitConcatOverflowError;
import ke.co.nectar.token.exceptions.InvalidDateTimeBitsException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidRegisterBitString;
import ke.co.nectar.token.generators.tokensdecoder.error.CRCError;

import java.util.HashMap;
import java.util.Optional;

public class ClearCreditToken extends Class2Token {

    private Optional<RandomNo> randomNo;
    private TokenIdentifier tokenIdentifier;
    private Optional<Register> register;

    public ClearCreditToken(String requestID)
            throws InvalidRangeException {
        super(requestID);
        setTokenClass(new ClearCreditTokenClass());
        setTokenSubClass(new ClearCreditTokenSubClass());
    }

    public ClearCreditToken(String requestID,
                            Optional<RandomNo> randomValue,
                            TokenIdentifier tokenIdentifier,
                            Optional<Register> register) throws InvalidRangeException {
        super(requestID);
        setTokenClass(new ClearCreditTokenClass());
        setTokenSubClass(new ClearCreditTokenSubClass());
        setRandomNo(randomValue);
        setTokenIdentifier(tokenIdentifier);
        setRegister(register);
    }

    @Override
    public String getType() {
        return "ClearCredit_21";
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

    public Optional<Register> getRegister() {
        return register;
    }

    public void setRegister(Optional<Register> register) {
        this.register = register;
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("class", getTokenClass().getBitString().getValue());
        params.put("subclass", getTokenSubClass().getBitString().getValue());
        params.put("token_identifier", getTokenIdentifier().getTimeOfIssue().toString());
        params.put("type", getType());

        if (getRegister().isPresent())
            params.put("register", getRegister().get().getBitString().getValue());

        return params;
    }

    public void decode(BitString decryptedTokenBitString,
                       BitString encryptedTokenBitString) throws
            InvalidRangeException, CRCError,
            InvalidDateTimeBitsException, InvalidRegisterBitString,
            BitConcatOverflowError  {
        if (checkCrc(decryptedTokenBitString, getTokenClass())) {
            setRandomNo(extractRandomNo(decryptedTokenBitString));
            setTokenIdentifier(extractTokenIdentifier(decryptedTokenBitString));
            setRegister(Optional.of(new Register(decryptedTokenBitString.extractBits(16, 16))));
            setEncryptedTokenBitString(Long.toBinaryString(encryptedTokenBitString.getValue()));
            setDecryptedTokenBitString(Long.toBinaryString(decryptedTokenBitString.getValue()));
        }
    }
}
