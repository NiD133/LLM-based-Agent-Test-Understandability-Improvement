package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately {

    // Transforms a numeric string to its Integer key for use in the index
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    @Test
    void testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately() {
        // Wrap an ArrayList with an indexed view
        final Collection<String> original = new ArrayList<>();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        // Add elements directly to the underlying collection, bypassing the index
        original.add("1");
        original.add("2");
        original.add("3");

        // The index is unaware of these additions, so lookups return null
        assertNull(indexed.get(1));
        assertNull(indexed.get(2));
        assertNull(indexed.get(3));

        // Rebuild the index from the current contents of the decorated collection
        indexed.reindex();

        // After reindex, lookups correctly map each Integer key to its String value
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }
}
