package ke.co.nectar.token.domain.token.class0;

import ke.co.nectar.token.domain.Amount;
import ke.co.nectar.token.domain.RandomNo;
import ke.co.nectar.token.domain.TokenIdentifier;
import ke.co.nectar.token.domain.base.BitString;
import ke.co.nectar.token.domain.token.Token;
import ke.co.nectar.token.domain.tokenclass.TokenClass;
import ke.co.nectar.token.domain.tokensubclass.TokenSubClass;
import ke.co.nectar.token.exceptions.InvalidBitStringException;
import ke.co.nectar.token.exceptions.InvalidRangeException;
import ke.co.nectar.token.exceptions.InvalidUnitsPurchasedBitsException;
import ke.co.nectar.token.exceptions.InvalidUnitsPurchasedException;
import ke.co.nectar.token.generators.utils.Utils;

import java.util.HashMap;
import java.util.Optional;

public abstract class Class0Token extends Token {

    private Optional<RandomNo> randomNo;
    private TokenIdentifier tokenIdentifier;
    private Amount amountPurchased;

    public Class0Token(String requestID) {
        super(requestID);
    }

    public Class0Token(String requestID,
                       TokenClass tokenClass,
                       TokenSubClass tokenSubClass,
                       Optional<RandomNo> random,
                       TokenIdentifier tokenIdentifier,
                       Amount amountPurchased) {
        super(requestID);
        setTokenClass(tokenClass);
        setTokenSubClass(tokenSubClass);
        if (random.isPresent())
            setRND(random);
        setTokenIdentifier(tokenIdentifier);
        setAmountPurchased(amountPurchased);
    }

    public Optional<RandomNo> getRandomNo() {
        return randomNo;
    }

    public void setRND(Optional<RandomNo> randomValue) {
        this.randomNo = randomValue ;
    }

    public TokenIdentifier getTokenIdentifier() {
        return tokenIdentifier ;
    }

    public void setTokenIdentifier(TokenIdentifier tokenIdentifier) {
        this.tokenIdentifier = tokenIdentifier ;
    }

    public Amount getAmountPurchased() {
        return amountPurchased ;
    }

    public void setAmountPurchased (Amount amountPurchased) {
        this.amountPurchased = amountPurchased ;
    }

    protected Amount extractAmount(BitString dataBlock)
            throws InvalidUnitsPurchasedBitsException, InvalidUnitsPurchasedException, InvalidRangeException, InvalidBitStringException {
        BitString unitsPurchasedBitString = dataBlock.extractBits(16, 16);
        return new Amount(Utils.convertToDouble(unitsPurchasedBitString));
    }

    @Override
    public HashMap<String, Object> getParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("class", getTokenClass().getBitString().getValue());
        params.put("subclass", getTokenSubClass().getBitString().getValue());
        params.put("token_identifier", getTokenIdentifier().getTimeOfIssue().toString());
        params.put("amount", getAmountPurchased().getAmountPurchased());
        params.put("type", getType());

        if (getCrc() != null)
            params.put("crc", getCrc().get().getBitString().getValue());

        if (getRandomNo() != null)
            params.put("rnd", getRandomNo().get().getBitString().getValue());

        return params;
    }
}
