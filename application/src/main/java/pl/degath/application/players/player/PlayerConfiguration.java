package pl.degath.application.players.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.degath.players.player.AddPlayerCommandHandler;
import pl.degath.players.player.GetAllPlayersQueryHandler;
import pl.degath.players.player.Player;
import pl.degath.players.player.RemovePlayerCommandHandler;
import pl.degath.players.player.UpdatePlayerCommandHandler;
import pl.degath.players.port.ExternalBankingApi;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;

@Configuration
public class PlayerConfiguration {

    @Autowired
    private Repository<Player> playerRepository;

    @Autowired
    private Repository<Team> teamRepository;

    @Bean
    public AddPlayerCommandHandler addPlayerCommandHandler(ExternalBankingApi externalBankingApi) {
        return new AddPlayerCommandHandler(playerRepository, teamRepository, externalBankingApi);
    }

    @Bean
    public GetAllPlayersQueryHandler getAllPlayersQueryHandler() {
        return new GetAllPlayersQueryHandler(playerRepository);
    }

    @Bean
    public RemovePlayerCommandHandler removePlayerCommandHandler() {
        return new RemovePlayerCommandHandler(playerRepository);
    }

    @Bean
    public UpdatePlayerCommandHandler updatePlayerCommandHandler() {
        return new UpdatePlayerCommandHandler(playerRepository, teamRepository);
    }
}
