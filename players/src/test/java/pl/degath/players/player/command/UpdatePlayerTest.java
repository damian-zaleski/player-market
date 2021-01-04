package pl.degath.players.player.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class UpdatePlayerTest {

    @Test
    void updatePlayer_withMissingPlayerId() {
        Throwable thrown = catchThrowable(() -> new RemovePlayer(null));

        assertThat(thrown)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Player id has to be specified.");
    }
}