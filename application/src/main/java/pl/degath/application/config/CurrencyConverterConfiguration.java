package pl.degath.application.config;

import com.posadskiy.currencyconverter.CurrencyConverter;
import com.posadskiy.currencyconverter.config.ConfigBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConverterConfiguration {

    @Value("${currency.api.key}")
    private String currencyApiKey;

    @Bean
    CurrencyConverter currencyConverter() {
        return new CurrencyConverter(
                new ConfigBuilder()
                        .currencyConverterApiApiKey(currencyApiKey)
                        .build());
    }
}
