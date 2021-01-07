package pl.degath.application.banking;

import com.posadskiy.currencyconverter.CurrencyConverter;
import org.springframework.stereotype.Service;
import pl.degath.banking.port.ExternalCurrencyExchangeApi;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * https://github.com/posadskiy/currency-converter
 * todo replace this adapter by consuming API directly: https://free.currencyconverterapi.com/
 */
@Service
public class ThirdPartyCurrencyConverter implements ExternalCurrencyExchangeApi {

    private final CurrencyConverter currencyConverter;

    public ThirdPartyCurrencyConverter(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    @Override
    public BigDecimal rate(Currency from, Currency to) {
        Double exchangeRate = currencyConverter.rate(from.getCurrencyCode(), to.getCurrencyCode());
        return BigDecimal.valueOf(exchangeRate);
    }
}


