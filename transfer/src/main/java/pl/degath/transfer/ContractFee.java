package pl.degath.transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

class ContractFee {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    //todo move these two to properties, as it should be configurable...
    private static final int MEANINGFUL_NAME_FOR_MAGIC_NUMBER = 100000;
    private static final BigDecimal MAXIMUM_COMMISSION_PERCENT = new BigDecimal(10)
            .setScale(0, RoundingMode.HALF_EVEN);

    private final BigDecimal transferFee;
    private final BigDecimal commissionFee;
    private final Currency currency;

    public ContractFee(Player player, BigDecimal commissionPercentage, Currency currency) {
        this.transferFee = calculateTransferFee(player.getMonthsOfExperience(), player.getAge());
        this.commissionFee = calculateCommissionFee(commissionPercentage);
        this.currency = currency;
    }

    private BigDecimal calculateTransferFee(long monthOfExperience, long age) {
        return BigDecimal.valueOf((monthOfExperience * MEANINGFUL_NAME_FOR_MAGIC_NUMBER) / age);
    }

    private BigDecimal calculateCommissionFee(BigDecimal commissionPercentage) {
        if (commissionPercentage.compareTo(MAXIMUM_COMMISSION_PERCENT) > 0) {
            throw new DoNotBeTooGreedyException();
        }
        return percentageFrom(this.transferFee, commissionPercentage);
    }

    public static BigDecimal percentageFrom(BigDecimal base, BigDecimal pct) {
        return base.multiply(pct).divide(ONE_HUNDRED, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getTransferFee() {
        return transferFee;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public Currency getCurrency() {
        return currency;
    }
}
