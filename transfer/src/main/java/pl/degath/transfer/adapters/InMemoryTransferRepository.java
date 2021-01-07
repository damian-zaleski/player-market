package pl.degath.transfer.adapters;

import pl.degath.transfer.Transfer;
import pl.degath.transfer.port.TransferRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryTransferRepository implements TransferRepository {

    private final Map<UUID, Transfer> entities;

    public InMemoryTransferRepository() {
        this.entities = new HashMap<>();
    }

    public List<Transfer> getAll() {
        return List.copyOf(entities.values());
    }


    @Override
    public Optional<Transfer> get(UUID id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public void save(Transfer entity) {
        entities.put(entity.getId(), entity);
    }

}
