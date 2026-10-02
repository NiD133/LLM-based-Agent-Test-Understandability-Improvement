package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that a unique {@link IndexedCollection} rejects a second element
 * whose index key collides with an element already present.
 */
public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /**
     * Derives the index key from a value by parsing it as an {@link Integer}.
     * Two strings that parse to the same integer therefore share an index key.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Creates an empty collection that enforces uniqueness of the integer index keys. */
    private IndexedCollection<Integer, String> makeUniqueCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() {
        final Collection<String> uniqueCollection = makeUniqueCollection();

        uniqueCollection.add("1");

        // Adding "1" again maps to the already-indexed key 1, which must be rejected.
        assertThrows(IllegalArgumentException.class, () -> uniqueCollection.add("1"));
    }
}
