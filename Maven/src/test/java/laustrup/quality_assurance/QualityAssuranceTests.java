package laustrup.quality_assurance;

import laustrup.quality_assurance.items.TestItems;
import laustrup.utilities.console.Printer;

import org.junit.jupiter.api.Test;

import static laustrup.quality_assurance.items.aaa.assertions.AssertionFailer.failing;

public class QualityAssuranceTests extends Tester<Object,Object> {

    @Test
    void testerTest() {
        String password = _password;

        test(t -> {
            asserting(_adding!=null
                    && _addings.length==3);

            set_createTestItems(false);
            beforeEach();
            asserting(_adding!=null
                    && _addings.length==3
                    && !password.equals(_password)
                    && _items==null);
            set_createTestItems(true);

            return end("testerTest");
        });
    }


    @Test
    void canResetItems() {
        test(t -> {
            try {
                act(e -> {
                    _items.resetItems();
                    return null;
                });

                success("Items are reset without any errors!");
            } catch (Exception e) {
                Printer.get_instance().print("Test items caught an Exception...", e);

                failing("Items could not be reset...", e);
            }

            return end("itemTest");
        });
    }
}
