package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies the contract documented on {@link IndexedCollection}: modifications made
 * directly to the decorated collection do not update the index, but a call to
 * {@link IndexedCollection#reindex()} brings the index back in sync.
 */
public class IndexedCollectionTest_testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately {

    /** Maps each stored String to its Integer index key (e.g. "1" -> 1). */
    private static final Transformer<String, Integer> STRING_TO_INTEGER_KEY = Integer::valueOf;

    private static IndexedCollection<Integer, String> uniquelyIndex(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, STRING_TO_INTEGER_KEY);
    }

    @Test
    void testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately() {
        // Decorate an empty collection, then add elements directly to the underlying
        // collection so the decorator's index is bypassed.
        final Collection<String> decorated = new ArrayList<>();
        final IndexedCollection<Integer, String> indexed = uniquelyIndex(decorated);

        decorated.add("1");
        decorated.add("2");
        decorated.add("3");

        // The index was not notified of the separate modifications, so lookups miss.
        assertNull(indexed.get(1));
        assertNull(indexed.get(2));
        assertNull(indexed.get(3));

        // Rebuilding the index from the current contents restores correct lookups.
        indexed.reindex();

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }
}
