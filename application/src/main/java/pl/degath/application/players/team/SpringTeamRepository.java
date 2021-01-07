package pl.degath.application.players.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

public interface SpringTeamRepository extends JpaRepository<TeamEntity, String> {
}