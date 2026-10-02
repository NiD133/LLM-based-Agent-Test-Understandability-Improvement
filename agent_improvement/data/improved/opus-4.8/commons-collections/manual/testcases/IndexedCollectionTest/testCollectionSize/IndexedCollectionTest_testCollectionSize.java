package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Collection#size()} / {@link Collection#isEmpty()} behaviour of
 * {@link IndexedCollection}.
 */
public class IndexedCollectionTest_testCollectionSize {

    /** Sample values used to populate a "full" collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** Derives an Integer index key from a String value. */
    private static final Transformer<String, Integer> KEY_TRANSFORMER = Integer::valueOf;

    /**
     * Wraps the given backing list in a non-unique {@link IndexedCollection},
     * mirroring how the production decorator is created.
     */
    private static Collection<String> indexedCollectionOf(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, KEY_TRANSFORMER);
    }

    @Test
    void testCollectionSize() {
        final Collection<String> emptyCollection = indexedCollectionOf(new ArrayList<>());
        assertEquals(0, emptyCollection.size(), "Size of new Collection is 0.");

        final Collection<String> fullCollection = indexedCollectionOf(new ArrayList<>(asList(FULL_ELEMENTS)));
        assertFalse(fullCollection.isEmpty(), "Size of full collection should be greater than zero");
    }
}
