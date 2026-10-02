package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testAddedObjectsCanBeRetrievedByKey {

    /**
     * Transforms a numeric string to its Integer value, used as the index key.
     * For example, "12" becomes key 12, so indexed.get(12) returns "12".
     */
    private static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.parseInt(input);
        }
    }

    /**
     * Creates a non-unique indexed collection that indexes strings by their integer value.
     */
    private IndexedCollection<Integer, String> makeTestCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
                new ArrayList<>(), new IntegerTransformer());
    }

    /**
     * Verifies that strings added individually and via addAll can each be retrieved
     * from the indexed collection using their integer key.
     */
    @Test
    void testAddedObjectsCanBeRetrievedByKey() {
        final Collection<String> coll = makeTestCollection();
        coll.add("12");
        coll.add("16");
        coll.add("1");
        coll.addAll(asList("2", "3", "4"));

        @SuppressWarnings("unchecked")
        final IndexedCollection<Integer, String> indexed = (IndexedCollection<Integer, String>) coll;
        assertEquals("12", indexed.get(12));
        assertEquals("16", indexed.get(16));
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
        assertEquals("4", indexed.get(4));
    }
}
