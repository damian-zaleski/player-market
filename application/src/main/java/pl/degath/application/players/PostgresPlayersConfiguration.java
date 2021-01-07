package pl.degath.application.players;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.degath.application.players.player.SpringPlayerRepositoryImpl;
import pl.degath.application.players.team.SpringTeamRepositoryImpl;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

@Configuration
@Profile("postgres")
public class PostgresPlayersConfiguration {

    @Bean
    public Repository<Player> playerRepository() {
        return new SpringPlayerRepositoryImpl();
    }

    @Bean
    public Repository<Team> teamRepository() {
        return new SpringTeamRepositoryImpl();
    }
}