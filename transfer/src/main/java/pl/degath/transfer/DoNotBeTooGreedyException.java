package pl.degath.transfer;

class DoNotBeTooGreedyException extends RuntimeException {

    public DoNotBeTooGreedyException() {
        super("Commission percentage has to be <= 10%.");
    }
}
