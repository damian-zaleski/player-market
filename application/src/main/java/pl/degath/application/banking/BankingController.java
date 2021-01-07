package pl.degath.application.banking;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.banking.BankingApi;

@RestController
@RequestMapping("api/v1/accounts")
@Api("Banking operations")
public class BankingController {

    private final BankingApi bankingApi;

    public BankingController(BankingApi bankingApi) {
        this.bankingApi = bankingApi;
    }

    @PostMapping("/transfer")
    @ApiOperation("Transfer money between bank accounts.")
    public void transfer(@RequestBody TransferMoneyRequest request) {
        bankingApi.transferMoney(request.toCommand());
    }
}
