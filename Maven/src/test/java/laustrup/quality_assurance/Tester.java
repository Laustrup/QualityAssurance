package laustrup.quality_assurance;

import laustrup.quality_assurance.inheritances.items.TestItems;
import laustrup.utilities.collections.lists.Liszt;
import laustrup.utilities.console.Printer;
import laustrup.services.RandomCreatorService;
import laustrup.quality_assurance.inheritances.aaa.assertions.Asserter;

import lombok.Setter;

import org.junit.jupiter.api.BeforeEach;

import java.util.Random;
import java.util.function.Supplier;

import static laustrup.quality_assurance.inheritances.aaa.assertions.AssertionFailer.failing;

/**
 * Adds the test method, that will log each action of the test into print.
 * Extends ARRANGER, ACTOR and ASSERTER.
 * Includes a random generated password, that will be random generated beforeEach,
 * along with other attributes such as expected, a random and other.
 * @param <T> The return type.
 */
public abstract class Tester<T> extends Asserter<T> {

    /**
     * In case testItems should create inheritances beforeEach, this should be true.
     * If this project isn't a Bandwich project, it is recommended to be false.
     */
    @Setter
    private boolean _createTestItems = true;

    /** The object that is to be expected to be asserted as the same as the actual. */
    protected Object _expected;

    /** Is used when there should be tested with an exception. */
    protected Exception _exception;

    /**
     * The object that is to be expected to be asserted as the same as the expected
     * and are the result from the act of the method, that will be tested.
     */
    protected Object _actual;

    /** Is used when there should be tested a specific index of a collection from a function. */
    protected int _index;

    /**
     * Contains different generated inheritances to use for testing.
     * Are being reset for each method.
     */
    protected TestItems _items;

    /** A default password, with the purpose of creating, logging in and various alike features. */
    protected String _password = RandomCreatorService.get_instance().generatePassword();

    /** This Random is the java Random utility, that can be reused throughout tests. */
    protected Random _random = new Random();

    /** * Will divide Strings in CSVSources. */
    protected final String _divider = "|";

    /** Defines a character that is used to separate CSVSources */
    protected final char _delimiter = '|';

    /**
     * Can be used for testing with multiple adds.
     * Contains three Artists if it is meant for Bandwich project,
     * otherwise it will be three unique numbers.
     */
    protected Object[] _addings;

    /**
     * Can be used for testing with a single add.
     * Contains one Artist.
     */
    protected Object _adding;

    /** Default constructor, will generate testItems. */
    protected Tester() {}

    /**
     * Custom constructor, that only will construct Bandwich testItems, if input is true.
     * Is only recommended, if the project is using Bandwich models.
     * @param createTestItems Will generate testItem for each beforeEach.
     */
    protected Tester(boolean createTestItems) {
        _createTestItems = createTestItems;
    }

    /**
     * Custom constructor, that only will construct Bandwich testItems, if input is true.
     * Is only recommended, if the project is using Bandwich models.
     * @param createTestItems Will generate testItem for each beforeEach.
     * @param adding The adding that will be reused for tests.
     */
    protected Tester(boolean createTestItems, Object adding) {
        _createTestItems = createTestItems;
        _adding = adding;
    }

    @BeforeEach
    void beforeEach() {
        _password = RandomCreatorService.get_instance().generatePassword();
        _expected = new String();
        _actual = new String();

        _addings = new Object[3];
        Liszt<Integer> indexes = new Liszt<>();

        if (_createTestItems) {
            if (_items == null)
                _items = new TestItems();
            else
                _items.resetItems();

            for (int i = 0; i < _addings.length; i++) {
                int index;

                do {
                    index = _random.nextInt(_items.get_artistAmount());
                } while (indexes.contains(index));
                indexes.add(index);

                _addings[i] = _items.get_artists()[index];
            }

            _adding = _items.get_artists()[_random.nextInt(_items.get_artistAmount())];
        } else {
            _items = null;
            _adding = 1;

            for (int i = 0; i < _addings.length; i++) {
                _addings[i] = i+2;
            }
        }
    }

    /**
     * Must be used before each test method, since it will catch exceptions and print test information.
     * @param supplier The test algorithm that will be supplied,
     *                 if the algorithm return false, something unmeant occurred.
     */
    protected void test(Supplier<String> supplier) {
        try {
            String response = supplier.get();
            if (response.equals(TestMessage.SUCCESS.get_content()))
                Printer.get_instance().print(_print);
            else
                failing(response);
        } catch (Exception e) {
            addToPrint("An exception was caught in the main test method...");
            addToPrint(e.getMessage());
            Printer.get_instance().print(_print, e);
            throw e;
        }
    }

    /**
     * Must be used before each test method, since it will catch exceptions and print test information.
     * @param runnable The test algorithm that will be run,
     *                 if the algorithm return false, something unmeant occurred.
     */
    protected void test(Runnable runnable) {
        try {
            runnable.run();
            Printer.get_instance().print(_print);
        } catch (Exception e) {
            addToPrint("An exception was caught in the main test method...");
            addToPrint(e.getMessage());
            Printer.get_instance().print(_print, e);
            throw e;
        }
    }

    /** Will set the TestItems into a new initiate. */
    public void resetItems() { _items = new TestItems(); }
}
