package pl.degath.transfer;

import java.util.UUID;

public class BuyerNotFoundException extends RuntimeException {
    public BuyerNotFoundException(UUID notExistingBuyerId) {
        super(String.format("Buyer with id [%s] not found.", notExistingBuyerId));
    }
}
