package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Verifies that IndexedCollection indexes pre-existing elements when wrapping an
 * already-populated collection (i.e. the constructor calls reindex()).
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testDecoratedCollectionIsIndexedOnCreation {

    /** Converts a numeric string to its Integer value, used as the index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Wraps a collection in a non-unique IndexedCollection keyed by integer value. */
    private Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /** Wraps a collection in a unique IndexedCollection keyed by integer value. */
    private IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /** Returns a full collection containing the elements {"1","3","5","7","2","4","6"}. */
    private Collection<String> makeFullCollection() {
        String[] elements = { "1", "3", "5", "7", "2", "4", "6" };
        return decorateCollection(new ArrayList<>(Arrays.asList(elements)));
    }

    /**
     * When a pre-populated collection is wrapped by IndexedCollection, all existing
     * elements must be present in the index so that get(key) works immediately.
     */
    @Test
    void testDecoratedCollectionIsIndexedOnCreation() throws Exception {
        final Collection<String> original = makeFullCollection();
        final IndexedCollection<Integer, String> indexed = decorateUniqueCollection(original);
        assertEquals("1", indexed.get(1));
        assertEquals("2", indexed.get(2));
        assertEquals("3", indexed.get(3));
    }
}
