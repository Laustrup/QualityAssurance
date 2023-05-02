package laustrup.quality_assurance.items.aaa.assertions;

import laustrup.quality_assurance.items.aaa.Actor;
import laustrup.utilities.console.Printer;

import static org.junit.jupiter.api.Assertions.fail;

public abstract class AssertionFailer<T,R> extends Actor<T,R> {

    /**
     * Will make the assertion fail.
     * @param message The reason of the fail described.
     * @param exception The exception caused.
     */
    public static void failing(String message, Exception exception) {
        Printer.get_instance().print(message, exception);
        failing(message);
    }

    /**
     * Will make the assertion fail.
     * @param message The reason of the fail described.
     */
    public static void failing(String message) {
        Printer.get_instance().print(message, new Exception());
        fail();
    }
}
