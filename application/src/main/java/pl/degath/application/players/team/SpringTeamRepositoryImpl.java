package pl.degath.application.players.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import pl.degath.players.port.Repository;
import pl.degath.players.team.Team;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpringTeamRepositoryImpl implements Repository<Team> {

    @Autowired
    private SpringTeamRepository springTeamRepository;

    @Override
    public Optional<Team> get(UUID id) {
        return springTeamRepository.findById(id.toString())
                .map(TeamEntity::toDomain);
    }

    @Override
    public List<Team> getAll() {
        return springTeamRepository.findAll().stream()
                .map(TeamEntity::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Page<Team> getAll(Pagination pagination) {
        org.springframework.data.domain.Page<Team> map = springTeamRepository.findAll(PageRequest.of(pagination.getPageNumber() - 1, pagination.getItemsPerPage()))
                .map(TeamEntity::toDomain);
        return new Page<>(map.getContent(), map.getTotalPages(), map.getPageable().getPageNumber());
    }

    @Override
    public Team save(Team entity) {
        TeamEntity saved = springTeamRepository.save(new TeamEntity(entity));
        return saved.toDomain();
    }

    @Override
    public void remove(UUID id) {
        springTeamRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsBy(Predicate<Team> predicate) {
        return getAll().stream()
                .anyMatch(predicate);
    }
}
