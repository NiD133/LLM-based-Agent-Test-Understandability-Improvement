package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Collection#add(Object)} on a non-unique {@link IndexedCollection}.
 */
public class IndexedCollectionTest_testCollectionAdd {

    /** Derives the Integer index key for a String element by parsing it. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements exercised by the test; each maps to a distinct index key. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Wraps {@code collection} in a non-unique IndexedCollection keyed by Integer value. */
    private static Collection<String> indexedCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /**
     * Asserts that {@code actual} contains exactly the elements of {@code expected},
     * irrespective of iteration order.
     */
    private static void assertSameElements(final Collection<String> expected,
                                           final Collection<String> actual) {
        assertEquals(expected.size(), actual.size(), "Collection size should match expected");
        final List<String> remaining = new ArrayList<>(actual);
        for (final String element : expected) {
            assertTrue(remaining.remove(element), "Missing expected element: " + element);
        }
        assertTrue(remaining.isEmpty(), "Collection has unexpected extra elements: " + remaining);
    }

    @Test
    void testCollectionAdd() {
        // Adding a single element to a fresh, empty collection always succeeds
        // and leaves the collection holding exactly that one element.
        for (final String element : FULL_ELEMENTS) {
            final Collection<String> collection = indexedCollection(new ArrayList<>());
            final List<String> expected = new ArrayList<>();

            final boolean added = collection.add(element);
            expected.add(element);

            assertSameElements(expected, collection);
            assertTrue(added, "Empty collection changed after add");
            assertEquals(1, collection.size(), "Collection size is 1 after first add");
        }

        // Adding every element in sequence grows the collection by one per successful add.
        final Collection<String> collection = indexedCollection(new ArrayList<>());
        final List<String> expected = new ArrayList<>();
        int expectedSize = 0;
        for (final String element : FULL_ELEMENTS) {
            final boolean added = collection.add(element);
            expected.add(element);

            assertSameElements(expected, collection);
            if (added) {
                expectedSize++;
            }
            assertEquals(expectedSize, collection.size(), "Collection size should grow after add");
            assertTrue(collection.contains(element), "Collection should contain added element");
        }
    }
}
