package pl.degath.application.banking;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.degath.banking.BankingApi;

@RestController
@RequestMapping("api/v1/accounts")
public class BankingController {

    private final BankingApi bankingApi;

    public BankingController(BankingApi bankingApi) {
        this.bankingApi = bankingApi;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferMoneyRequest request) {
        bankingApi.transferMoney(request.toCommand());
    }
}
