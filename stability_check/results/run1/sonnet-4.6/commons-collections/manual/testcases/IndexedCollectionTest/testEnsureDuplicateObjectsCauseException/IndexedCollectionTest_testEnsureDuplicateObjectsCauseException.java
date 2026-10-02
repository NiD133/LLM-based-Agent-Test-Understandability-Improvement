package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /**
     * Transforms a String element into its Integer value to serve as the index key.
     */
    static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /**
     * Creates a unique-indexed collection backed by an ArrayList.
     * Adding an element whose key is already in the index throws IllegalArgumentException.
     */
    private IndexedCollection<Integer, String> makeUniqueTestCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    /**
     * Verifies that adding a duplicate element to a unique-indexed collection
     * throws an IllegalArgumentException on the second add.
     */
    @Test
    void testEnsureDuplicateObjectsCauseException() {
        final Collection<String> coll = makeUniqueTestCollection();
        coll.add("1");
        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }
}
