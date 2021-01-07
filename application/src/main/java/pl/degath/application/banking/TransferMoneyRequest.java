package pl.degath.application.banking;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.degath.banking.command.TransferMoney;
import pl.degath.shared.infrastructure.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

public class TransferMoneyRequest {
    private final UUID fromOwnerId;
    private final UUID toOwnerId;
    private final String amount;
    private final String currencyCode;

    @JsonCreator
    public TransferMoneyRequest(@JsonProperty("fromOwnerId") UUID fromOwnerId,
                                @JsonProperty("toOwnerId") UUID toOwnerId,
                                @JsonProperty("amount") String amount,
                                @JsonProperty("currencyCode") String currencyCode) {
        this.fromOwnerId = fromOwnerId;
        this.toOwnerId = toOwnerId;
        this.amount = amount;
        this.currencyCode = currencyCode;
    }

    public UUID getFromOwnerId() {
        return fromOwnerId;
    }

    public UUID getToOwnerId() {
        return toOwnerId;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @JsonIgnore
    public TransferMoney toCommand() {
        return new TransferMoney(fromOwnerId, toOwnerId, new Money(new BigDecimal(amount), parseToCurrency(currencyCode)));
    }

    @JsonIgnore
    private Currency parseToCurrency(String stringCurrency) {
        Currency currency;
        try {
            currency = Currency.getInstance(stringCurrency);
        } catch (IllegalArgumentException ex) {
            String message = String.format("Given currency: '%s' is invalid.", stringCurrency);
            throw new InvalidCurrencyException(message, ex);
        }
        return currency;
    }
}
