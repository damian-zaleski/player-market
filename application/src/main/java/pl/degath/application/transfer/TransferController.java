package pl.degath.application.transfer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.transfer.TransferApi;

@RestController
@RequestMapping("api/v1/transfers")
public class TransferController {

    private final TransferApi transferApi;

    public TransferController(TransferApi transferApi) {
        this.transferApi = transferApi;
    }

    @PostMapping("/start")
    public void transfer(@RequestBody StartNewTransferRequest request) {
        transferApi.startNewTransfer(request.toCommand());
    }
}
