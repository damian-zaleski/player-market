package pl.degath.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.degath.application.banking.SpringBankAccountRepositoryImpl;
import pl.degath.application.players.player.SpringPlayerRepositoryImpl;
import pl.degath.application.players.team.SpringTeamRepositoryImpl;
import pl.degath.application.transfer.SpringTransferRepositoryImpl;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.transfer.port.TransferRepository;

@Configuration
@Profile("postgres")
public class PostgresConfiguration {

    @Bean
    @Primary
    public Repository<Player> playerRepository() {
        return new SpringPlayerRepositoryImpl();
    }

    @Bean
    @Primary
    public Repository<Team> teamRepository() {
        return new SpringTeamRepositoryImpl();
    }

    @Bean
    @Primary
    public BankAccountRepository bankAccountRepository() {
        return new SpringBankAccountRepositoryImpl();
    }

    @Bean
    @Primary
    public TransferRepository transferRepository() {
        return new SpringTransferRepositoryImpl();
    }
}