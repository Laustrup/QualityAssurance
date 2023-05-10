package laustrup.quality_assurance;

import laustrup.quality_assurance.inheritances.items.TestItems;
import org.junit.jupiter.api.Test;

import static laustrup.quality_assurance.inheritances.aaa.assertions.AssertionFailer.failing;

public class QualityAssuranceTests extends Tester<TestItems> {

    @Test
    void testerTest() {
        String password = _password;

        test(() -> {
            arrange();

            act(() -> {
                asserting(_adding!=null
                        && _addings.length==3);

                set_createTestItems(false);
                beforeEach();
                asserting(_adding!=null
                        && _addings.length==3
                        && !password.equals(_password)
                        && _items==null);
                set_createTestItems(true);
            });
        });
    }

    //TODO Won't change values, but they are also set to null before setup.
//    @Test
//    void canResetItems() {
//        test(() -> {
//            try {
//                TestItems before = arrange(() -> _items);
//
//                act(() -> _items.resetItems());
//
//                if (!compare(before,_items))
//                    success("Items are reset without any errors!\n \n\tItems are:\n \n"+_items.toString());
//                else
//                    failing("Items are not reset...");
//            } catch (Exception e) {
//                failing("Items could not be reset...", e);
//            }
//        });
//    }

    /**
     * Checks if the attributes of each testItems is alike.
     * @param before The previous items.
     * @param after The new items.
     * @return True if they are the same.
     */
    private boolean compare(TestItems before, TestItems after) {
        if (!itemsAreTheSame(before.get_participants(),after.get_participants())) return false;
        if (!itemsAreTheSame(before.get_artists(),after.get_artists())) return false;
        if (!itemsAreTheSame(before.get_bands(),after.get_bands())) return false;
        if (!itemsAreTheSame(before.get_venues(),after.get_venues())) return false;
        if (!itemsAreTheSame(before.get_events(),after.get_events())) return false;
        if (!itemsAreTheSame(before.get_countries(),after.get_countries())) return false;
        if (!itemsAreTheSame(before.get_phones(),after.get_phones())) return false;
        if (!itemsAreTheSame(before.get_addresses(),after.get_addresses())) return false;
        if (!itemsAreTheSame(before.get_contactInfo(),after.get_contactInfo())) return false;
        if (!itemsAreTheSame(before.get_albums(),after.get_albums())) return false;
        if (!itemsAreTheSame(before.get_ratings(),after.get_ratings())) return false;
        return itemsAreTheSame(before.get_chatRooms(), after.get_chatRooms());
    }

    /**
     * Loop that is used for comparing to collections.
     * @param before The previous items.
     * @param after The new items.
     * @return True if they are the same.
     */
    private boolean itemsAreTheSame(Object[] before, Object[] after) {
        if (before.length == after.length) {
            for (int i = 0; i < before.length; i++)
                if (!before[i].toString().equals(after[i].toString()))
                    return false;
        }
        else
            return false;

        return true;
    }

    @Test
    void canAssert() {
        test(() -> {
            try {
                arrange();

                act(() -> {
                    asserting(_items.get_ratings(),_items.get_ratings());
                    asserting(_items.get_participants(),_items.get_participants());
                    asserting(_items.get_artists(),_items.get_artists());
                    asserting(_items.get_bands(),_items.get_bands());
                    asserting(_items.get_venues(),_items.get_venues());
                    asserting(_items.get_events(),_items.get_events());
                    asserting(_items.get_albums(),_items.get_albums());
                    asserting(_items.get_addresses(),_items.get_addresses());
                    asserting(_items.get_phones(),_items.get_phones());
                    asserting(_items.get_contactInfo(),_items.get_contactInfo());
                    asserting(_items.get_chatRooms(),_items.get_chatRooms());
                });
            } catch (Exception e) {
                failing("Exception caught in asserting...",e);
            }
        });
    }
}
