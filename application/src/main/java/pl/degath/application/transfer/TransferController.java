package pl.degath.application.transfer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.transfer.TransferApi;

@RestController
@RequestMapping("api/v1/transfers")
@Api("Transfer operations.")
public class TransferController {

    private final TransferApi transferApi;

    public TransferController(TransferApi transferApi) {
        this.transferApi = transferApi;
    }

    @PostMapping("/start")
    @ApiOperation("Transfer players between teams.")
    public void transfer(@RequestBody StartNewTransferRequest request) {
        transferApi.startNewTransfer(request.toCommand());
    }
}
