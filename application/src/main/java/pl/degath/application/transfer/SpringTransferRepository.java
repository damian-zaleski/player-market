package pl.degath.application.transfer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringTransferRepository extends JpaRepository<TransferEntity, String> {
}
