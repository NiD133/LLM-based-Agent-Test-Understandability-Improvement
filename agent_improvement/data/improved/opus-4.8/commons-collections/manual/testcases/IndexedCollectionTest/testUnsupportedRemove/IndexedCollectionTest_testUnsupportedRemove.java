package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link IndexedCollection} rejects removal operations with an
 * {@link UnsupportedOperationException} when removal is not supported.
 *
 * <p>The collection produced here <em>does</em> support removal
 * ({@link #isRemoveSupported()} returns {@code true}), so the test guards on
 * that flag and exits early. The removal assertions below document the
 * behaviour that would be checked for a removal-unsupported collection.</p>
 */
public class IndexedCollectionTest_testUnsupportedRemove {

    /**
     * Transforms a string element into the {@link Integer} used as its index key.
     */
    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements used to populate a full collection. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain collection holding the elements {@link #collection} is expected to contain. */
    private Collection<String> confirmed;

    private Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    /** Whether the collection supports remove/removeAll/retainAll/clear/iterator.remove(). */
    private boolean isRemoveSupported() {
        return true;
    }

    /** Resets {@link #collection} and {@link #confirmed} to empty collections. */
    private void resetEmpty() {
        collection = decorateCollection(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets {@link #collection} and {@link #confirmed} to full collections. */
    private void resetFull() {
        collection = decorateCollection(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /** Verifies that {@link #collection} and {@link #confirmed} hold the same elements. */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
                "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
                "Collection isEmpty() should match confirmed collection's");

        final List<String> remaining = new ArrayList<>(confirmed);
        for (final String element : collection) {
            if (!remaining.remove(element)) {
                fail("Collection contains a value not present in the confirmed collection: " + element);
            }
        }
        if (!remaining.isEmpty()) {
            fail("Collection is missing values present in the confirmed collection: " + remaining);
        }
    }

    /**
     * If removal is unsupported, every removal operation must raise an
     * {@link UnsupportedOperationException} without altering the collection.
     */
    @Test
    void testUnsupportedRemove() {
        if (isRemoveSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(UnsupportedOperationException.class, () -> collection.clear(),
                "clear should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> collection.remove(null),
                "remove should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> collection.removeIf(e -> true),
                "removeIf should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> collection.removeAll(null),
                "removeAll should raise UnsupportedOperationException");
        verify();
        assertThrows(UnsupportedOperationException.class, () -> collection.retainAll(null),
                "retainAll should raise UnsupportedOperationException");
        verify();

        resetFull();
        final Iterator<String> iterator = collection.iterator();
        iterator.next();
        assertThrows(UnsupportedOperationException.class, () -> iterator.remove(),
                "iterator.remove should raise UnsupportedOperationException");
        verify();
    }
}
