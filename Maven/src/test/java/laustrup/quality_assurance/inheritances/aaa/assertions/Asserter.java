package laustrup.quality_assurance.inheritances.aaa.assertions;

import laustrup.quality_assurance.inheritances.aaa.Actor;
import laustrup.utilities.collections.lists.Liszt;
import laustrup.utilities.console.Printer;
import laustrup.models.Rating;
import laustrup.models.albums.Album;
import laustrup.models.chats.ChatRoom;
import laustrup.models.chats.Request;
import laustrup.models.chats.messages.Bulletin;
import laustrup.models.chats.messages.Mail;
import laustrup.models.events.Event;
import laustrup.models.events.Gig;
import laustrup.models.events.Participation;
import laustrup.models.users.User;
import laustrup.models.users.contact_infos.ContactInfo;
import laustrup.models.users.sub_users.Performer;
import laustrup.models.users.sub_users.bands.Artist;
import laustrup.models.users.sub_users.bands.Band;
import laustrup.models.users.sub_users.participants.Participant;
import laustrup.models.users.sub_users.venues.Venue;
import laustrup.models.users.subscriptions.Subscription;
import laustrup.utilities.parameters.Plato;

import javax.lang.model.element.UnknownElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Will do assertions, mostly specified for Bandwich objects
 * @param <T> The return type.
 */
public abstract class Asserter<T> extends Actor<T> {

    /**
     * Generates a message for cases, where an element such or should not be null.
     * @param object The Object that should either be null or not.
     * @return The generated message.
     */
    protected String isNullMessage(Object object) {
        if (object == null)
            return "An object is null, where it is not supposed to be...";
        return object.toString() + " isn't null...";
    }

    /**
     * Will generate a message of the assertion fail of an unknown switch case occurring at default case.
     * @param element The element that results in the fail.
     * @return The generated message.
     */
    protected String switchElementUnknown(Object element) {
        return element.toString() + " type unknown of " + element.getClass();
    }

    /**
     * Asserts two Users to check they are the same.
     * @param expected The User that is arranged and defined.
     * @param actual The User that is the result of an action.
     * @param authority The authority of both Users.
     */
    protected void asserting(User expected, User actual, User.Authority authority) {
        Printer.get_instance().print("Expected = " + expected + "\n\nActual = " + actual);
        switch (authority) {
            case PARTICIPANT -> assertParticipants((Participant) expected, (Participant) actual);
            case BAND -> assertBand((Band) expected,(Band) actual);
            case ARTIST -> assertArtists((Artist) expected,(Artist) actual);
            case VENUE -> assertion((Venue) expected,(Venue) actual);
            default -> AssertionFailer.failing(switchElementUnknown(authority), new UnknownElementException(null,authority));
        }
    }

    /**
     * Asserts two Participants to check they are the same.
     * @param expected The Participant that is arranged and defined.
     * @param actual The Participant that is the result of an action.
     */
    protected void assertParticipants(Participant expected, Participant actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertUsers(expected, actual);
            if (AssertionChecker.allowLiszt(expected.get_idols(),actual.get_idols()))
                AssertionActor.assertFor(1,expected.get_idols().size(), i -> {
                    assertEquals(expected.get_idols().Get(i).toString(),actual.get_idols().Get(i).toString());
                    return null;
            });

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Bands to check they are the same.
     * @param expected The Band that is arranged and defined.
     * @param actual The Band that is the result of an action.
     */
    protected void assertBand(Band expected, Band actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertPerformers(expected, actual);
            if (AssertionChecker.allowLiszt(expected.get_members(),actual.get_members()))
                AssertionActor.assertFor(1,expected.get_members().size(), i -> {
                    assertEquals(expected.get_members().Get(i).toString(),actual.get_members().Get(i).toString());
                    return null;
            });
            assertEquals(expected.get_runner(),actual.get_runner());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Artists to check they are the same.
     * @param expected The Artist that is arranged and defined.
     * @param actual The Artist that is the result of an action.
     */
    protected void assertArtists(Artist expected, Artist actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertPerformers(expected,actual);
            if (AssertionChecker.allowLiszt(expected.get_bands(),actual.get_bands())) {
                AssertionActor.assertFor(1,expected.get_bands().size(), i -> {
                    assertEquals(expected.get_bands().Get(i).toString(),actual.get_bands().Get(i).toString());
                    return null;
                });
            }

            assertEquals(expected.get_runner(),actual.get_runner());
            assertRequests(expected.get_requests(),actual.get_requests());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Performers to check they are the same.
     * @param expected The Performer that is arranged and defined.
     * @param actual The Performer that is the result of an action.
     */
    private void assertPerformers(Performer expected, Performer actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertUsers(expected, actual);
            if (AssertionChecker.allowLiszt(expected.get_fans(),actual.get_fans())) {
                AssertionActor.assertFor(1,expected.get_fans().size(), i -> {
                    assertEquals(expected.get_fans().Get(i).toString(),actual.get_fans().Get(i).toString());
                    return null;
                });
            }
            if (AssertionChecker.allowLiszt(expected.get_idols(),actual.get_idols())) {
                AssertionActor.assertFor(1,expected.get_idols().size(), i -> {
                    assertEquals(expected.get_idols().Get(i).toString(),actual.get_idols().Get(i).toString());
                    return null;
                });
            }

            return AssertionMessage.SUCCESS.get_content();
        });

    }

    /**
     * Asserts two Venues to check they are the same.
     * @param expected The Venue that is arranged and defined.
     * @param actual The Venue that is the result of an action.
     */
    protected void assertion(Venue expected, Venue actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertUsers(expected, actual);
            assertEquals(expected.get_location(),actual.get_location());
            assertEquals(expected.get_gearDescription(),actual.get_gearDescription());
            assertEquals(expected.get_size(),actual.get_size());
            assertRequests(expected.get_requests(),actual.get_requests());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Users to check they are the same.
     * @param expected The User that is arranged and defined.
     * @param actual The User that is the result of an action.
     */
    private void assertUsers(User expected, User actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            AssertionActor.assertIf(AssertionChecker.allowId(expected.get_primaryId(),actual.get_primaryId()),
                expected.get_primaryId(),actual.get_primaryId());
            assertEquals(expected.get_username(),actual.get_username());
            assertEquals(expected.get_firstName(),actual.get_firstName());
            assertEquals(expected.get_lastName(),actual.get_lastName());
            assertEquals(expected.get_description(),actual.get_description());
            asserting(expected.get_contactInfo(),actual.get_contactInfo());
            assertAlbums(expected.get_albums(),actual.get_albums());
            assertRatings(expected.get_ratings(),actual.get_ratings());
            assertEvents(expected.get_events(),actual.get_events());
            assertChatRooms(expected.get_chatRooms(),actual.get_chatRooms());
            asserting(expected.get_subscription(),actual.get_subscription());
            assertBulletins(expected.get_bulletins(),actual.get_bulletins());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Albums to check they are the same.
     * @param expectations The Albums that are arranged and defined.
     * @param actuals The Albums that are the result of an action.
     */
    protected void assertAlbums(Liszt<Album> expectations, Liszt<Album> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations, actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                Album expected = expectations.Get(i),
                        actual = actuals.Get(i);

                if (!expected.get_items().isEmpty() && !actual.get_items().isEmpty()) {
                    for (int j = 1; j <= expected.get_items().size(); j++) {
                        AssertionActor.assertFor(new int[]{1,j},expected.get_items().Get(j).get_tags().size(), indexes -> {
                            assertEquals(expected.get_items().Get(indexes[1]).get_tags().Get(indexes[0]).get_primaryId(),
                                    actual.get_items().Get(indexes[1]).get_tags().Get(indexes[0]).get_primaryId());
                            return null;
                        });
                        assertEquals(expected.get_items().Get(j).get_endpoint(),actual.get_items().Get(j).get_endpoint());
                        AssertionActor.assertIf(expected.get_items().Get(j).get_event() != null,
                                expected.get_items().Get(j).get_event() != null ?
                                        expected.get_items().Get(j).get_event().get_primaryId() : 0,
                                actual.get_items().Get(j).get_primaryId());
                        assertEquals(expected.get_items().Get(j).get_kind(),actual.get_items().Get(j).get_kind());
                    }
                }
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Ratings to check they are the same.
     * @param expectations The Ratings that are arranged and defined.
     * @param actuals The Ratings that are the result of an action.
     */
    protected void assertRatings(Liszt<Rating> expectations, Liszt<Rating> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                Rating expected = expectations.Get(i),
                        actual = actuals.Get(i);

                AssertionActor.assertIf(AssertionChecker.allowObjects(expected.get_appointed(),actual.get_appointed()),
                        expected.get_appointed().get_primaryId(),actual.get_appointed().get_primaryId());

                AssertionActor.assertIf(AssertionChecker.allowObjects(expected.get_judge(),actual.get_judge()),
                        expected.get_judge().get_primaryId(),actual.get_judge().get_primaryId());
                assertEquals(expected.get_value(),actual.get_value());
                asserting((expected.get_value() <= 5 && expected.get_value() > 0)
                        && (actual.get_value() <= 5 && actual.get_value() > 0));
                assertEquals(expected.get_comment(),actual.get_comment());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts ChatRooms to check they are the same.
     * @param expectations The ChatRooms that are arranged and defined.
     * @param actuals The ChatRooms that are the result of an action.
     */
    public void assertChatRooms(Liszt<ChatRoom> expectations, Liszt<ChatRoom> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                ChatRoom expected = expectations.Get(i),
                        actual = actuals.Get(i);
                assertMails(expected.get_mails(),actual.get_mails());
                AssertionActor.assertFor(1,expected.get_chatters().size(), index -> {
                    assertEquals(expected.get_chatters().Get(index).toString(),actual.get_chatters().Get(index).toString());
                    return null;
                });
                assertEquals(expected.get_responsible().toString(),actual.get_responsible().toString());
                assertEquals(expected.get_answeringTime(),actual.get_answeringTime());
                assertEquals(expected.is_answered(),actual.is_answered());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Mails to check they are the same.
     * @param expectations The Mails that are arranged and defined.
     * @param actuals The Mails that are the result of an action.
     */
    protected void assertMails(Liszt<Mail> expectations, Liszt<Mail> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                Mail expected = expectations.Get(i), actual = actuals.Get(i);

                assertEquals(expected.get_primaryId(),actual.get_primaryId());
                assertEquals(expected.get_chatRoom().toString(),actual.get_chatRoom().toString());
                assertEquals(expected.get_author().toString(),actual.get_author().toString());
                assertEquals(expected.get_content(),actual.get_content());
                assertEquals(expected.is_sent(),actual.is_sent());
                assertEquals(expected.get_edited().toString(),actual.get_edited().toString());
                assertEquals(expected.is_public(),actual.is_public());
                assertEquals(expected.get_timestamp(),actual.get_timestamp());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Subscriptions to check they are the same.
     * @param expected The Subscription that is arranged and defined.
     * @param actual The Subscription that is the result of an action.
     */
    protected void asserting(Subscription expected, Subscription actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertEquals(expected.get_user().toString(),actual.get_user().toString());
            assertEquals(expected.get_type(),actual.get_type());
            assertEquals(expected.get_status(),actual.get_status());
            assertEquals(expected.get_price(),actual.get_price());
            assertEquals(expected.get_cardId(),actual.get_cardId());

            if (AssertionChecker.allowObjects(expected.get_offer(),actual.get_offer())) {
                AssertionActor.assertIf(actual.get_offer().get_expires() != null,
                    expected.get_offer().get_expires(),actual.get_offer().get_expires());
                AssertionActor.assertIf(actual.get_offer().get_type() != null,
                    expected.get_offer().get_type(),actual.get_offer().get_type());
                AssertionActor.assertIf(actual.get_offer().get_effect() > 0,
                    expected.get_offer().get_effect(),actual.get_offer().get_effect());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Requests to check they are the same.
     * @param expectations The Requests that are arranged and defined.
     * @param actuals The Requests that are the result of an action.
     */
    protected void assertRequests(Liszt<Request> expectations, Liszt<Request> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                Request expected = expectations.Get(i),
                        actual = actuals.Get(i);
                assertUsers(expected.get_user(),actual.get_user());
                asserting(expected.get_event(),actual.get_event());
                assertEquals(expected.get_approved().get_truth(),actual.get_approved().get_truth());
                assertEquals(expected.get_message(),actual.get_message());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two ContactInfos to check they are the same.
     * @param expected The information that is arranged and defined.
     * @param actual The information that is the result of an action.
     */
    protected void asserting(ContactInfo expected, ContactInfo actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertEquals(expected.get_email(),actual.get_email());
            assertEquals(expected.get_country().get_title(),actual.get_country().get_title());
            assertEquals(expected.get_country().get_indexes(),actual.get_country().get_indexes());
            assertEquals(expected.get_phone().get_country().get_firstPhoneNumberDigits(),actual.get_phone().get_country().get_firstPhoneNumberDigits());
            assertEquals(expected.get_phone().get_numbers(),actual.get_phone().get_numbers());
            assertEquals(expected.get_phone().is_mobile(),actual.get_phone().is_mobile());
            assertEquals(expected.getAddressInfo(),actual.getAddressInfo());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Bulletins to check they are the same.
     * @param expectations The Bulletins that are arranged and defined.
     * @param actuals The Bulletins that are the result of an action.
     */
    protected void assertBulletins(Liszt<Bulletin> expectations, Liszt<Bulletin> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++) {
                Bulletin expected = expectations.Get(i), actual = actuals.Get(i);

                assertEquals(expected.get_primaryId(),actual.get_primaryId());
                assertUsers(expected.get_author(), actual.get_author());
                AssertionActor.assertIf(AssertionChecker.allowObjects(expected.get_author(),actual.get_author()),expected.get_receiver(),actual.get_receiver());
                AssertionActor.assertIf(AssertionChecker.allowObjects(expected.get_receiver(),actual.get_receiver()),expected.get_receiver(),actual.get_receiver());
                assertEquals(expected.get_content(),actual.get_content());
                assertEquals(expected.is_sent(),actual.is_sent());
                asserting(expected.get_edited(),actual.get_edited());
                assertEquals(expected.is_public(),actual.is_public());
            }

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Events to check they are the same.
     * @param expectations The Events that are arranged and defined.
     * @param actuals The Events that are the result of an action.
     */
    protected void assertEvents(Liszt<Event> expectations, Liszt<Event> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++)
                asserting(expectations.Get(i),actuals.Get(i));

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Events to check they are the same.
     * @param expected The Event that is arranged and defined.
     * @param actual The Event that is the result of an action.
     */
    protected void asserting(Event expected, Event actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected, actual);

            AssertionActor.assertIf(
                AssertionChecker.allowObjects(expected.get_openDoors(),actual.get_openDoors()),
                expected.get_openDoors(),actual.get_openDoors()
            );
            AssertionActor.assertIf(
                AssertionChecker.allowObjects(expected.get_start(),actual.get_start()),
                    expected.get_start(),actual.get_start()
            );
            AssertionActor.assertIf(
                AssertionChecker.allowObjects(expected.get_end(),actual.get_end()),
                expected.get_end(),actual.get_end()
            );
            AssertionActor.assertIf(
                AssertionChecker.allowObjects(expected.get_contactInfo(),actual.get_contactInfo()),
                expected.get_contactInfo(),actual.get_contactInfo()
            );
            AssertionActor.assertIf(
                expected.get_cancelled()!=null&&actual.get_cancelled()!=null,
                () -> asserting(expected.get_cancelled(), actual.get_cancelled())
            );
            AssertionActor.assertIf(
                expected.get_soldOut()!=null&&actual.get_soldOut()!=null,
                () -> asserting(expected.get_soldOut(), actual.get_soldOut())
            );
            AssertionActor.assertIf(
                expected.get_voluntary()!=null&&actual.get_voluntary()!=null,
                () -> asserting(expected.get_voluntary(), actual.get_voluntary())
            );
            AssertionActor.assertIf(
                expected.get_public()!=null&&actual.get_public()!=null,
                () -> asserting(expected.get_public(), actual.get_public())
            );

            assertEquals(expected.get_length(),expected.get_length());
            assertEquals(expected.get_location(), actual.get_location());
            assertEquals(expected.get_ticketsURL(), actual.get_ticketsURL());

            AssertionActor.assertIf(
                expected.get_gigs()!=null&&actual.get_gigs()!=null,
                () -> assertGigs(expected.get_gigs(),actual.get_gigs())
            );
            AssertionActor.assertIf(
                expected.get_venue()!=null&&actual.get_venue()!=null,
                () -> assertion(expected.get_venue(),actual.get_venue())
            );
            AssertionActor.assertIf(
                expected.get_requests() != null && actual.get_requests() != null,
                () -> assertRequests(expected.get_requests(), actual.get_requests())
            );
            AssertionActor.assertIf(
                expected.get_participations() != null && actual.get_participations() != null,
                () -> assertParticipations(expected.get_participations(), actual.get_participations())
            );
            AssertionActor.assertIf(
                expected.get_albums()!=null&&actual.get_albums()!=null,
                () -> assertAlbums(expected.get_albums(),actual.get_albums())
            );

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Participations to check they are the same.
     * @param expectations The Participations that are arranged and defined.
     * @param actuals The Participations that are the result of an action.
     */
    protected void assertParticipations(Liszt<Participation> expectations, Liszt<Participation> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++)
                asserting(expectations.Get(i),actuals.Get(i));

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Participations to check they are the same.
     * @param expected The Participation that is arranged and defined.
     * @param actual The Participation that is the result of an action.
     */
    protected void asserting(Participation expected, Participation actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertEquals(expected.toString(),actual.toString());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts Gigs to check they are the same.
     * @param expectations The Gigs that are arranged and defined.
     * @param actuals The Gigs that are the result of an action.
     */
    protected void assertGigs(Liszt<Gig> expectations, Liszt<Gig> actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowLiszt(expectations,actuals))
                return AssertionChecker.lisztMessage(expectations,actuals);

            for (int i = 1; i <= expectations.size(); i++)
                asserting(expectations.Get(i),actuals.Get(i));

            return AssertionMessage.SUCCESS.get_content();
        });
    }



    /**
     * Asserts two Gigs to check they are the same.
     * @param expected The Gig that is arranged and defined.
     * @param actual The Gig that is the result of an action.
     */
    protected void asserting(Gig expected, Gig actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            asserting(expected.get_event(),actual.get_event());
            asserting(expected.get_act(),actual.get_act());
            assertEquals(expected.get_start(),actual.get_start());
            assertEquals(expected.get_end(),actual.get_end());

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two acts to check they are the same.
     * @param expectations The acts that is arranged and defined.
     * @param actuals The acts that is the result of an action.
     */
    protected void asserting(Performer[] expectations, Performer[] actuals) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowCollection(expectations,actuals))
                return AssertionChecker.collectionMessage(expectations,actuals);

            for (int i = 0; i < expectations.length; i++)
                assertPerformers(expectations[i], actuals[i]);

            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Asserts two Platos to check they are the same.
     * @param expected The act that is arranged and defined.
     * @param actual The act that is the result of an action.
     */
    protected void asserting(Plato expected, Plato actual) {
        asserting(expected.toString(),expected.toString());
    }

    /**
     * Is used to determine whether a statement is true.
     * Uses the assertTrue method.
     * @param isTrue A statement or value that must be true.
     */
    protected void asserting(boolean isTrue) {
        AssertionActor.doAssert(() -> {
            assertTrue(isTrue);
            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * A simple comparing assertEqual of two Objects.
     * @param expected The Object that is arranged and defined.
     * @param actual The Object that is the result of an action
     */
    protected void asserting(Object expected, Object actual) {
        AssertionActor.doAssert(() -> {
                if (!AssertionChecker.allowObjects(expected,actual))
                    return AssertionChecker.objectMessage(expected,actual);

                assertEquals(expected,actual);
                return AssertionMessage.SUCCESS.get_content();
            }
        );
    }

    /**
     * A simple comparing assertEqual of two Strings.
     * @param expected The String that is arranged and defined.
     * @param actual The String that is the result of an action
     */
    protected void asserting(String expected, String actual) {
        AssertionActor.doAssert(() -> {
            if (!AssertionChecker.allowObjects(expected,actual))
                return AssertionChecker.objectMessage(expected,actual);

            assertEquals(expected, actual);
            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Will assert the condition of whether this is null or not.
     * @param object The Object that will be asserted.
     */
    protected void notNull(Object object) {
        AssertionActor.doAssert(() -> {
            assertNotNull(object);
            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Will add the message to the print and assertion status will be a success.
     * @param message A description of the assertion.
     */
    protected void success(String message) {
        AssertionActor.doAssert(() -> {
            _print += "\n\n\t" + message + "\n";
            assertTrue(true);
            return AssertionMessage.SUCCESS.get_content();
        });
    }

    /**
     * Will assert success, if the runnable throws an Exception.
     * Otherwise, it will fail.
     * It is managed with a try/catch.
     * @param runnable A Lambda function, that should throw an Exception.
     */
    protected void assertException(Runnable runnable) {
        try {
            runnable.run();
            fail("The runnable didn't throw an exception...");
        } catch (Exception e) {
            success("The runnable threw an Exception! Which was: \n" + e.getMessage());
        }
    }
}
