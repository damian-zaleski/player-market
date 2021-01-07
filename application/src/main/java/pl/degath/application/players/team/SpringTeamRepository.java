package pl.degath.application.players.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringTeamRepository extends JpaRepository<TeamEntity, String> {
}