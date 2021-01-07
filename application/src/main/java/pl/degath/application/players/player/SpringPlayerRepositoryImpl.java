package pl.degath.application.players.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import pl.degath.players.player.Player;
import pl.degath.players.port.Repository;
import pl.degath.shared.infrastructure.Page;
import pl.degath.shared.infrastructure.Pagination;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpringPlayerRepositoryImpl implements Repository<Player> {

    @Autowired
    private SpringPlayerRepository springPlayerRepository;

    @Override
    public Optional<Player> get(UUID id) {
        return springPlayerRepository.findById(id.toString())
                .map(PlayerEntity::toDomain);
    }

    @Override
    public List<Player> getAll() {
        return springPlayerRepository.findAll().stream()
                .map(PlayerEntity::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Page<Player> getAll(Pagination pagination) {
        org.springframework.data.domain.Page<Player> map = springPlayerRepository.findAll(PageRequest.of(pagination.getPageNumber() - 1, pagination.getItemsPerPage()))
                .map(PlayerEntity::toDomain);
        return new Page<>(map.getContent(), map.getTotalPages(), map.getPageable().getPageNumber());
    }

    @Override
    public Player save(Player entity) {
        PlayerEntity saved = springPlayerRepository.save(new PlayerEntity(entity));
        return saved.toDomain();
    }

    @Override
    public void remove(UUID id) {
        springPlayerRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsBy(Predicate<Player> predicate) {
        return getAll().stream()
                .anyMatch(predicate);
    }
}
