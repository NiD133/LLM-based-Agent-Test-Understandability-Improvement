package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link IndexedCollection#clear()}.
 *
 * <p>An {@link IndexedCollection} decorates a backing collection and keeps an
 * index in sync with it. This test verifies that {@code clear()} empties the
 * decorator just like it empties a plain {@link ArrayList} that mirrors the same
 * contents.</p>
 */
public class IndexedCollectionTest_testCollectionClear {

    /** Maps each String element to its Integer value so it can be used as an index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Sample contents used to build a non-empty collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Wraps the given collection in a non-unique {@link IndexedCollection}. */
    private IndexedCollection<Integer, String> indexed(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /**
     * Asserts that {@code actual} contains exactly the same elements as
     * {@code expected}, ignoring iteration order.
     */
    private void assertSameContents(final Collection<String> expected, final Collection<String> actual) {
        assertEquals(expected.size(), actual.size(), "size");
        assertEquals(expected.isEmpty(), actual.isEmpty(), "isEmpty()");
        assertTrue(new HashSet<>(actual).equals(new HashSet<>(expected)), "elements");
    }

    @Test
    void testCollectionClear() {
        // Clearing an already-empty IndexedCollection is a no-op and must not fail.
        final IndexedCollection<Integer, String> emptyCollection = indexed(new ArrayList<>());
        emptyCollection.clear();
        assertSameContents(new ArrayList<>(), emptyCollection);

        // Clearing a populated IndexedCollection empties it, matching a cleared ArrayList.
        final IndexedCollection<Integer, String> fullCollection = indexed(new ArrayList<>(asList(FULL_ELEMENTS)));
        final Collection<String> confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
        fullCollection.clear();
        confirmed.clear();
        assertSameContents(confirmed, fullCollection);
    }
}
