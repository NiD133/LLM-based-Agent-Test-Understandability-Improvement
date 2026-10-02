package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Collection#isEmpty()} as implemented by {@link IndexedCollection}.
 *
 * <p>An {@code IndexedCollection} is a decorator that keeps an index of its
 * elements keyed by a transformer. This test verifies that {@code isEmpty()}
 * reports correctly for a freshly created collection and for a populated one,
 * and that calling {@code isEmpty()} does not mutate the collection.</p>
 */
public class IndexedCollectionTest_testCollectionIsEmpty {

    /** Sample values used to build a populated ("full") collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Derives each element's index key by parsing the String as an Integer. */
    private static final Transformer<String, Integer> KEY_TRANSFORMER = Integer::valueOf;

    /** Creates an empty {@link IndexedCollection} backed by an {@link ArrayList}. */
    private static Collection<String> newEmptyCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), KEY_TRANSFORMER);
    }

    /** Creates an {@link IndexedCollection} pre-populated with {@link #FULL_ELEMENTS}. */
    private static Collection<String> newFullCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(asList(FULL_ELEMENTS)), KEY_TRANSFORMER);
    }

    /**
     * Asserts that {@code actual} holds exactly the same elements as
     * {@code expected}, ignoring iteration order and allowing duplicates.
     * This mirrors the reference ("confirmed") collection comparison used by
     * the original test to ensure {@code isEmpty()} left the contents intact.
     */
    private static void assertSameContents(final Collection<String> expected,
                                           final Collection<String> actual) {
        assertEquals(expected.size(), actual.size(), "size should match the reference collection");
        assertEquals(expected.isEmpty(), actual.isEmpty(),
                "isEmpty() should match the reference collection");

        final List<String> unmatched = new ArrayList<>(expected);
        for (final String element : actual) {
            assertTrue(unmatched.remove(element),
                    () -> "collection contains an unexpected element: " + element);
        }
        assertTrue(unmatched.isEmpty(),
                () -> "collection is missing expected elements: " + unmatched);
    }

    /**
     * Tests {@link Collection#isEmpty()}.
     */
    @Test
    void testCollectionIsEmpty() {
        final Collection<String> empty = newEmptyCollection();
        assertTrue(empty.isEmpty(), "A newly created collection should be empty.");
        // isEmpty() must not change the contents.
        assertSameContents(new ArrayList<>(), empty);

        final Collection<String> full = newFullCollection();
        assertFalse(full.isEmpty(), "A populated collection should not be empty.");
        // isEmpty() must not change the contents.
        assertSameContents(new ArrayList<>(asList(FULL_ELEMENTS)), full);
    }
}
