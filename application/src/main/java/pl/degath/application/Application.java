package pl.degath.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.degath.banking.BankAccount;
import pl.degath.banking.port.BankAccountRepository;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.shared.infrastructure.Entity;
import pl.degath.shared.infrastructure.Money;
import pl.degath.transfer.port.TransferRepository;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Year;
import java.time.YearMonth;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //todo remove this and add schema with test data.
    @Bean
    CommandLineRunner init(Repository<Team> teamRepository,
                           Repository<Player> playerRepository,
                           BankAccountRepository bankAccountRepository,
                           TransferRepository transferRepository) {
        return run -> {

            Team realMadryt = new Team(UUID.fromString("a482c512-8548-4e65-9900-23d173f052ff"), "Real Madryt", Currency.getInstance("USD"));
            Player sergioRamos = new Player(UUID.fromString("5eddf473-1796-46a2-bdf8-86e5e89a8d35"),
                    "Sergio Ramos",
                    realMadryt.getId(),
                    Year.of(1986),
                    YearMonth.of(2014, 1));
            Player edenHazard = new Player(UUID.fromString("b375cf87-2c97-4286-ba12-b299e33a3816"),
                    "Eden Hazard",
                    realMadryt.getId(),
                    Year.of(1987),
                    YearMonth.of(2011, 5));
            Player karimBenzema = new Player(UUID.fromString("0d2e5f5c-8ca2-48b0-943d-7ca161b3538c"),
                    "Eden Hazard",
                    realMadryt.getId(),
                    Year.of(1988),
                    YearMonth.of(2010, 6));

            Team bayernMonachium = new Team(UUID.fromString("a3299519-eea3-4360-90c8-1b1b4ecd3279"), "Bayern Monachium", Currency.getInstance("EUR"));
            Player robertLewandowski = new Player(UUID.fromString("adf9523e-4f25-49ea-8297-8b55cdf2ad09"),
                    "Robert Lewandowski",
                    bayernMonachium.getId(),
                    Year.of(1989),
                    YearMonth.of(2005, 1));
            Player davidAlaba = new Player(UUID.fromString("ddd919c2-19a0-4a65-aa25-565650285949"),
                    "David Alaba",
                    bayernMonachium.getId(),
                    Year.of(1990),
                    YearMonth.of(2007, 5));
            Player leroySane = new Player(UUID.fromString("effa6737-826d-4a9b-8c81-6c1fd014b7a8"),
                    "Leroy Sane",
                    bayernMonachium.getId(),
                    Year.of(1991),
                    YearMonth.of(2007, 6));

            Team juventusFC = new Team(UUID.fromString("0f227838-860f-4f80-8cd5-3b2d77f5d2d6"), "Juventus F.C.", Currency.getInstance("EUR"));
            Player christianoRonaldo = new Player(UUID.fromString("ef2509a9-b57a-46d8-8a20-69a188d67c7b"),
                    "Christiano Ronaldo",
                    bayernMonachium.getId(),
                    Year.of(1992),
                    YearMonth.of(2008, 1));
            Player federicoChiesa = new Player(UUID.fromString("ddd919c2-19a0-4a65-aa25-565650285949"),
                    "Federico Chiesa",
                    bayernMonachium.getId(),
                    Year.of(1993),
                    YearMonth.of(2009, 5));
            Player pauloDybala = new Player(UUID.fromString("f5f88d00-edcf-4547-bc9c-cdf3e8b4f435"),
                    "Paulo Dybala",
                    bayernMonachium.getId(),
                    Year.of(1994),
                    YearMonth.of(2010, 6));

            var teams = List.of(realMadryt, bayernMonachium, juventusFC);
            var players = List.of(sergioRamos, edenHazard, karimBenzema, robertLewandowski, davidAlaba, leroySane, christianoRonaldo, federicoChiesa, pauloDybala);
            addAll(teamRepository, bankAccountRepository, teams);
            addAll(playerRepository, bankAccountRepository, players);
        };
    }

    private <T extends Entity> void addAll(Repository<T> repository, BankAccountRepository bankAccountRepository, List<T> elements) {
        elements.forEach(entity -> {
            repository.save(entity);
            bankAccountRepository.save(randomizedAccount(entity));
        });
    }

    private <T extends Entity> BankAccount randomizedAccount(T entity) {
        var randomFactor = new SecureRandom().nextInt() % 2 == 0;
        BigDecimal bigDecimal = randomFactor ? new BigDecimal("5000000") : new BigDecimal("0");
        Currency currency = randomFactor ? Currency.getInstance("USD") : Currency.getInstance("EUR");
        return new BankAccount(UUID.randomUUID(), entity.getId(), new Money(bigDecimal, currency));
    }

}
