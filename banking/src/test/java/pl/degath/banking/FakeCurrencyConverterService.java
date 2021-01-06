package pl.degath.banking;

import pl.degath.banking.port.ExternalCurrencyExchangeApi;

import java.math.BigDecimal;
import java.util.Currency;

public class FakeCurrencyConverterService implements ExternalCurrencyExchangeApi {

    @Override
    public BigDecimal rate(Currency from, Currency to) {
        if (from.getCurrencyCode().equals("PLN") && to.getCurrencyCode().equals("USD")) {
            return new BigDecimal("0.271884");
        } else if (from.getCurrencyCode().equals("USD") && to.getCurrencyCode().equals("PLN")) {
            return new BigDecimal("3.677206");
        } else {
            return null;
        }
    }
}
