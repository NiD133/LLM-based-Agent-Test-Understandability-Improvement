package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that a uniquely indexed {@link IndexedCollection} rejects a second
 * element whose index key already exists.
 */
public class IndexedCollectionTest_testEnsureDuplicateObjectsCauseException {

    /** Maps a String value to its Integer index key, e.g. "1" -> 1. */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Creates a unique-index collection keyed by the integer value of each String. */
    private IndexedCollection<Integer, String> makeUniqueIndexedCollection() {
        return IndexedCollection.uniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    @Test
    void testEnsureDuplicateObjectsCauseException() {
        final Collection<String> coll = makeUniqueIndexedCollection();

        // First element with key "1" is accepted.
        coll.add("1");

        // A second element mapping to the same key violates the uniqueness
        // constraint and must throw.
        assertThrows(IllegalArgumentException.class, () -> coll.add("1"));
    }
}
