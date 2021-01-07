package pl.degath.application.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.degath.banking.BankingApi;
import pl.degath.banking.BankingService;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.banking.port.ExternalCurrencyExchangeApi;
import pl.degath.banking.port.ExternalPlayerApi;
import pl.degath.banking.port.ExternalTeamApi;

@Configuration
public class BankingConfiguration {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Bean
    public BankingApi bankingApi(ExternalPlayerApi externalPlayerApi,
                                 ExternalTeamApi externalTeamApi,
                                 ExternalCurrencyExchangeApi externalCurrencyExchangeApi) {
        return new BankingService(bankAccountRepository, externalPlayerApi, externalTeamApi, externalCurrencyExchangeApi);
    }
}
