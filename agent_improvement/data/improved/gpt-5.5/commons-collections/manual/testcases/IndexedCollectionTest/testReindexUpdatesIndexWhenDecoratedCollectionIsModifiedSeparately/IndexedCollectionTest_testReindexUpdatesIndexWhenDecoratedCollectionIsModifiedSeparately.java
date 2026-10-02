package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately {

    private static final Transformer<String, Integer> STRING_TO_INTEGER = new IntegerTransformer();

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, STRING_TO_INTEGER);
    }

    @Test
    void testReindexUpdatesIndexWhenDecoratedCollectionIsModifiedSeparately() throws Exception {
        final Collection<String> original = new ArrayList<>();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);

        original.add("1");
        original.add("2");
        original.add("3");

        assertNull(indexed.get(1));
        assertNull(indexed.get(2));
        assertNull(indexed.get(3));

        indexed.reindex();

        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }
}
