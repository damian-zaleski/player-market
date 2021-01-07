package pl.degath.application.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import pl.degath.transfer.Transfer;
import pl.degath.transfer.port.TransferRepository;

import java.util.Optional;
import java.util.UUID;

public class SpringTransferRepositoryImpl implements TransferRepository {

    @Autowired
    private SpringTransferRepository springTransferRepository;

    @Override
    public Optional<Transfer> get(UUID transferId) {
        return springTransferRepository.findById(transferId.toString())
                .map(TransferEntity::toDomain);
    }

    @Override
    public void save(Transfer transfer) {
        springTransferRepository.save(new TransferEntity(transfer));
    }
}
