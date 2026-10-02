package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#containsAll(Collection)}.
 *
 * <p>The collection under test is an {@link IndexedCollection} that decorates an
 * {@link ArrayList} of numeric strings and indexes them by their integer value.
 * Each assertion is mirrored against a plain {@link ArrayList} ("confirmed"
 * collection) so that {@link #verify()} can prove that {@code containsAll} never
 * mutates the collection.</p>
 */
public class IndexedCollectionTest_testCollectionContainsAll {

    /** Elements held by a "full" collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Elements that are never present in a "full" collection. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /** Transforms a numeric string into the integer used as its index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain collection known to hold exactly the same elements as {@link #collection}. */
    private Collection<String> confirmed;

    /** Wraps the given collection in a non-unique IndexedCollection keyed by integer value. */
    private Collection<String> decorate(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = decorate(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to hold every element of {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = decorate(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /** Asserts that {@link #collection} still holds exactly the same elements as {@link #confirmed}. */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(), "Collection size should match confirmed collection's");
        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String value : collection) {
            assertTrue(remaining.remove(value),
                    "Collection should not contain a value missing from the confirmed collection: " + value);
        }
        assertTrue(remaining.isEmpty(),
                "Collection should contain every value of the confirmed collection; missing: " + remaining);
    }

    @Test
    void testCollectionContainsAll() {
        // An empty collection contains all elements of an empty collection...
        resetEmpty();
        final Collection<String> query = new HashSet<>();
        assertTrue(collection.containsAll(query),
                "Every Collection should contain all elements of an empty Collection.");

        // ...but not the elements of a non-empty collection.
        query.addAll(asList(OTHER_ELEMENTS));
        assertFalse(collection.containsAll(query),
                "Empty Collection shouldn't contain all elements of a non-empty Collection.");
        verify();

        // A full collection does not contain the "other" elements...
        resetFull();
        assertFalse(collection.containsAll(query),
                "Full collection shouldn't contain other elements");

        // ...but does contain all of its own full elements.
        query.clear();
        query.addAll(asList(FULL_ELEMENTS));
        assertTrue(collection.containsAll(query),
                "Full collection should containAll full elements");
        verify();

        // A full collection contains a partial slice of the full elements
        // (indices 2..4: "5", "7", "2"), and contains itself.
        final Collection<String> partial = asList(FULL_ELEMENTS).subList(2, 5);
        assertTrue(collection.containsAll(partial),
                "Full collection should containAll partial full elements");
        assertTrue(collection.containsAll(collection),
                "Full collection should containAll itself");
        verify();

        // A full collection contains the full elements even when listed twice.
        final Collection<String> withDuplicates = new ArrayList<>(asList(FULL_ELEMENTS));
        withDuplicates.addAll(asList(FULL_ELEMENTS));
        assertTrue(collection.containsAll(withDuplicates),
                "Full collection should containAll duplicate full elements");
        verify();
    }
}
