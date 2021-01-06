package pl.degath.transfer;

import pl.degath.transfer.command.StartNewTransfer;

interface TransferApi {
    void startNewTransfer(StartNewTransfer command);
}
