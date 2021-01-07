package pl.degath.transfer;

import pl.degath.transfer.command.StartNewTransfer;

public interface TransferApi {
    void startNewTransfer(StartNewTransfer command);
}
