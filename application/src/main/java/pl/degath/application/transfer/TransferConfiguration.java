package pl.degath.application.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.degath.transfer.TransferApi;
import pl.degath.transfer.TransferService;
import pl.degath.transfer.port.ExternalBankAccountApi;
import pl.degath.transfer.port.ExternalPlayerApi;
import pl.degath.transfer.port.ExternalTeamApi;
import pl.degath.transfer.port.TransferRepository;

@Configuration
public class TransferConfiguration {

    @Autowired
    private TransferRepository transferRepository;

    @Bean
    public TransferApi transferApi(ExternalBankAccountApi externalBankAccountApi,
                                   ExternalPlayerApi externalPlayerApi,
                                   ExternalTeamApi externalTeamApi) {
        return new TransferService(externalBankAccountApi, externalPlayerApi, externalTeamApi, transferRepository);
    }
}
