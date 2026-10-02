package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a uniquely-indexed collection rejects duplicate entries.
 *
 * <p>An {@link IndexedCollection} created with {@code uniqueIndexedCollection} enforces that
 * every element maps to a distinct index key. Adding a second element that produces the same
 * key must throw {@link IllegalArgumentException}.
 */
public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /**
     * Transforms a numeric String to its Integer value, used as the index key.
     * Two strings that parse to the same integer (e.g. "1" and "1") share a key
     * and are therefore considered duplicates in a unique index.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() throws Exception {
        // A uniquely-indexed collection allows only one element per index key.
        IndexedCollection<Integer, String> uniqueCollection =
                IndexedCollection.uniqueIndexedCollection(new ArrayList<String>(), new IntegerTransformer());

        // First insertion succeeds: key 1 is not yet in the index.
        uniqueCollection.add("1");

        // Second insertion of "1" maps to the same key (1), violating the uniqueness constraint.
        assertThrows(IllegalArgumentException.class, () -> uniqueCollection.add("1"));
    }
}
