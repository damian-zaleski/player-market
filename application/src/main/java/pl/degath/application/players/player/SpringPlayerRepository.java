package pl.degath.application.players.player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringPlayerRepository extends JpaRepository<PlayerEntity, String> {
}
