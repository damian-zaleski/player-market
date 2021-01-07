package pl.degath.application.players;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.degath.players.player.Player;
import pl.degath.players.port.InMemoryRepository;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

@Configuration
@Profile("!postgres")
public class InMemoryPlayersConfiguration {

    @Bean
    public Repository<Player> playerRepository() {
        return new InMemoryRepository<>();
    }

    @Bean
    public Repository<Team> teamRepository() {
        return new InMemoryRepository<>();
    }
}