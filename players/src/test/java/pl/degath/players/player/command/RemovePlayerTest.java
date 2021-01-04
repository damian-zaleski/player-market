package pl.degath.players.player.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RemovePlayerTest {

    @Test
    void removePlayer_withMissingPlayerId() {
        Throwable thrown = catchThrowable(() -> new RemovePlayer(null));

        assertThat(thrown)
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Player id has to be specified.");
    }
}