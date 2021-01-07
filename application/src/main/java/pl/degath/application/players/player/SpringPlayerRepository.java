package pl.degath.application.players.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringPlayerRepository extends JpaRepository<PlayerEntity, String> {
}
