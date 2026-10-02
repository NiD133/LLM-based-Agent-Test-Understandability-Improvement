package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a uniquely-indexed {@link IndexedCollection} rejects a second
 * element that maps to an already-present index key.
 */
public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /**
     * Maps a String value to its Integer index key, so that two Strings parsing
     * to the same number are treated as duplicate keys by the unique index.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /**
     * Creates an empty collection guarded by a unique index keyed on the
     * integer value of each element.
     */
    private IndexedCollection<Integer, String> makeUniqueTestCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() {
        final Collection<String> collection = makeUniqueTestCollection();

        // The first element with key 1 is accepted.
        collection.add("1");

        // A second element mapping to the same key 1 violates the uniqueness
        // constraint and must be rejected.
        assertThrows(IllegalArgumentException.class, () -> collection.add("1"));
    }
}
