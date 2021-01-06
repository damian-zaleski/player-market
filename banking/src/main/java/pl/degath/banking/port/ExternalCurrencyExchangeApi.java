package pl.degath.banking.port;

import java.math.BigDecimal;
import java.util.Currency;

public interface ExternalCurrencyExchangeApi {
    BigDecimal rate(Currency from, Currency to);
}
