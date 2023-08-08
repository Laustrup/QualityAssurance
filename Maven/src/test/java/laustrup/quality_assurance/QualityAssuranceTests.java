package laustrup.quality_assurance;

import org.junit.jupiter.api.Test;

import static laustrup.quality_assurance.inheritances.aaa.assertions.AssertionFailer.failing;

public class QualityAssuranceTests extends Tester<Object> {

    public QualityAssuranceTests() {
        _editor = new TestEditor() {
            @Override
            public void beforeEach() {
                _adding = "Test-Value";
            }
        };
    }

    @Test
    void beforeEachTest() {
        test(() -> {
            arrange();
            asserting(_adding == "Test-Value");
        });
    }

    @Test
    void testerTest() {
        String password = _password;

        test(() -> {
            arrange();

            act(() -> {
                    asserting(_adding!=null
                    && _addings.length==3);

                beforeEach();
                asserting(_adding!=null
                    && _addings.length==3
                    && !password.equals(_password)
                );
            });
        });
    }

    @Test @SuppressWarnings("all")
    void failsOnException() {
        assertException(() -> {
            test(() -> {
                arrange();
                Integer.parseInt("String");
            });
            test(() -> {
                arrange();
                Integer.parseInt("String");
                return TestMessage.SUCCESS.get_content();
            });
        });
    }

    @Test
    void canAssert() {
        test(() -> {
            try {
                arrange();
                act(() -> asserting(true));
            } catch (Exception e) {
                failing("Exception caught in asserting...",e);
            }
        });
    }
}
