package pl.degath.application.players.player;

import pl.degath.players.player.Player;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Year;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerEntity {
    @Id
    private String id;
    private String name;
    private String teamId;
    private String yearOfBirth;
    private String yearOfCareerStart;
    private String monthOfCareerStart;

    public PlayerEntity() {
    }

    public PlayerEntity(Player player) {
        this.id = player.getId().toString();
        this.name = player.getName();
        this.teamId = player.getTeamId().toString();
        this.yearOfBirth = player.getYearOfBirth().toString();
        this.yearOfCareerStart = String.valueOf(player.getCareerStart().getYear());
        this.monthOfCareerStart = String.valueOf(player.getCareerStart().getMonthValue());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public String getYearOfCareerStart() {
        return yearOfCareerStart;
    }

    public String getMonthOfCareerStart() {
        return monthOfCareerStart;
    }

    public Player toDomain() {
        return new Player(
                UUID.fromString(id),
                name,
                UUID.fromString(teamId),
                Year.of(Integer.parseInt(yearOfBirth)),
                YearMonth.of(Integer.parseInt(yearOfCareerStart), Integer.parseInt(monthOfCareerStart)));
    }
}
