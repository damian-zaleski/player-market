package pl.degath.application.players.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.AddTeamCommandHandler;
import pl.degath.players.team.GetPlayersTeamsQueryHandler;
import pl.degath.players.team.RemoveTeamCommandHandler;
import pl.degath.players.team.Team;
import pl.degath.players.team.UpdateTeamCommandHandler;

/**
 * todo field injection is related with https://stackoverflow.com/questions/40695893/spring-security-circular-bean-dependency
 * should consider different way to solve it
 */
@Configuration
public class TeamConfiguration {

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private Repository<Team> teamRepository;

    @Bean
    public AddTeamCommandHandler addTeamCommandHandler() {
        return new AddTeamCommandHandler(teamRepository);
    }

    @Bean
    public GetPlayersTeamsQueryHandler getPlayersTeamsQueryHandler() {
        return new GetPlayersTeamsQueryHandler(teamRepository, playerRepository);
    }

    @Bean
    public RemoveTeamCommandHandler removeTeamCommandHandler() {
        return new RemoveTeamCommandHandler(teamRepository, playerRepository);
    }

    @Bean
    public UpdateTeamCommandHandler updateTeamCommandHandler() {
        return new UpdateTeamCommandHandler(teamRepository);
    }
}
