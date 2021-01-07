package pl.degath.application.players.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.degath.players.player.Player;
import pl.degath.players.port.ExternalBankingApi;
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

    @Value("${initial.bank.amount:5000000}")
    private String initialAmount;

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private Repository<Team> teamRepository;

    @Bean
    public AddTeamCommandHandler addTeamCommandHandler(ExternalBankingApi externalBankingApi) {
        return new AddTeamCommandHandler(teamRepository, externalBankingApi, initialAmount);
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
