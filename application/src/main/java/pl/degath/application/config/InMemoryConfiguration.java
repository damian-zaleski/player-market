package pl.degath.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.degath.banking.adapters.InMemoryBankAccountRepository;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.players.player.Player;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

@Configuration
@Profile("!postgres")
public class InMemoryConfiguration {

    @Bean
    public Repository<Player> playerRepository() {
        return new InMemoryRepository<>();
    }

    @Bean
    public Repository<Team> teamRepository() {
        return new InMemoryRepository<>();
    }

    @Bean
    public BankAccountRepository bankAccountRepository() {
        return new InMemoryBankAccountRepository();
    }
}