package pl.degath.players.player.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.degath.shared.infrastructure.ValidationException;

import java.time.Year;
import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class AddPlayerTest {

    private static final UUID NOT_EXISTING_TEAM_ID = UUID.fromString("ac8fad99-43c5-4c1a-90e1-b9e8c12bbfae");

    @ParameterizedTest
    @ValueSource(strings = {"", "            "})
    void addPlayer_withBlankName_throwsException(String input) {
        Throwable thrown = catchThrowable(() -> new AddPlayer(input, NOT_EXISTING_TEAM_ID, YearMonth.of(2010,10), Year.of(2010)));

        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessage("Name of new player has to be specified.");
    }

    @Test
    void addPlayer_withMissingName_throwsException() {
        Throwable thrown = catchThrowable(() -> new AddPlayer(null, NOT_EXISTING_TEAM_ID, YearMonth.of(2010,10), Year.of(2010)));

        assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessage("Name of new player has to be specified.");
    }

}