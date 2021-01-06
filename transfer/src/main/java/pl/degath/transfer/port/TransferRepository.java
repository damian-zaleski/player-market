package pl.degath.transfer.port;

import pl.degath.transfer.Transfer;

import java.util.Optional;
import java.util.UUID;

public interface TransferRepository {

    Optional<Transfer> get(UUID transferId);

    void save(Transfer transfer);
}
