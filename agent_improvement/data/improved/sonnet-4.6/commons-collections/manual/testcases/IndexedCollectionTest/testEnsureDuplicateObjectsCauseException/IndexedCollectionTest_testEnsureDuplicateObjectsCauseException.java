package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /**
     * Transforms a numeric string to its Integer value, used as the index key.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /**
     * Creates a uniquely-indexed collection: adding a duplicate key must throw
     * {@link IllegalArgumentException}.
     */
    private Collection<String> makeUniqueTestCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    /**
     * Verifies that inserting an element whose key is already present in a
     * unique-indexed collection raises {@link IllegalArgumentException}.
     */
    @Test
    void testEnsureDuplicateObjectsCauseException() {
        final Collection<String> coll = makeUniqueTestCollection();
        coll.add("1");
        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }
}
