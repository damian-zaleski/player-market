package pl.degath.shared.infrastructure;

public interface CommandHandler<T extends Command> {
    void handle(T command);
}
