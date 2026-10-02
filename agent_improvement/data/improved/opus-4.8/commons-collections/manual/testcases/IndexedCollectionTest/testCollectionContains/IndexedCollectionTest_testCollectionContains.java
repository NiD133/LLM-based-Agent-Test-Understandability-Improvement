package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#contains(Object)}.
 *
 * <p>The collection under test is an {@link IndexedCollection} that indexes
 * {@link String} values by their integer value (see {@link IntegerTransformer}).
 * Because {@code contains} is answered through the index, this test confirms that
 * lookups behave correctly for both empty and fully-populated collections.</p>
 */
public class IndexedCollectionTest_testCollectionContains {

    /** Elements present in a "full" collection; all are distinct integers. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Elements that are never present in a "full" collection. */
    private static final String[] OTHER_ELEMENTS = { "9", "88", "678", "87", "98", "78", "99" };

    /**
     * Transforms a numeric String into its Integer value, used as the index key.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The IndexedCollection being tested. */
    private Collection<String> collection;

    /** A plain collection holding the same elements, used to confirm state is unchanged. */
    private Collection<String> confirmed;

    /**
     * Wraps the given collection in a non-unique IndexedCollection keyed by integer value.
     */
    private Collection<String> indexByInteger(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets both collections to an empty state. */
    private void resetEmpty() {
        collection = indexByInteger(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets both collections to hold all {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = indexByInteger(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /**
     * Verifies the indexed collection still holds exactly the same elements as the
     * confirmed collection, proving that calling {@code contains} did not mutate it.
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(), "Collection size should match confirmed collection's");
        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String value : collection) {
            assertTrue(remaining.remove(value),
                    "Collection contains an element not present in the confirmed collection: " + value);
        }
        assertTrue(remaining.isEmpty(),
                "Collection is missing elements present in the confirmed collection: " + remaining);
    }

    @Test
    void testCollectionContains() {
        // An empty collection contains none of the full elements...
        resetEmpty();
        for (int i = 0; i < FULL_ELEMENTS.length; i++) {
            assertFalse(collection.contains(FULL_ELEMENTS[i]), "Empty collection shouldn't contain element[" + i + "]");
        }
        verify(); // contains() must not have mutated the collection

        // ...nor any of the other elements.
        for (int i = 0; i < OTHER_ELEMENTS.length; i++) {
            assertFalse(collection.contains(OTHER_ELEMENTS[i]), "Empty collection shouldn't contain element[" + i + "]");
        }
        verify();

        // A full collection contains every one of the full elements...
        resetFull();
        for (int i = 0; i < FULL_ELEMENTS.length; i++) {
            assertTrue(collection.contains(FULL_ELEMENTS[i]), "Full collection should contain element[" + i + "]");
        }
        verify();

        // ...but none of the other elements.
        resetFull();
        for (final String element : OTHER_ELEMENTS) {
            assertFalse(collection.contains(element), "Full collection shouldn't contain element");
        }
    }
}
