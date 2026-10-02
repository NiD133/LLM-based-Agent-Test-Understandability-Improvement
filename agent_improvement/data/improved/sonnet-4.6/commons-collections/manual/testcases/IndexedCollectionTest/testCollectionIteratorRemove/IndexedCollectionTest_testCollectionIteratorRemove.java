package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionIteratorRemove {

    // Transforms a String to its Integer value, used as the index key
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // --- Test fixture ---

    private static final String[] FULL_ELEMENTS = {"1", "3", "5", "7", "2", "4", "6"};

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** Plain ArrayList that mirrors the expected state of {@code collection}. */
    private Collection<String> confirmed;

    private Collection<String> makeObject() {
        return IndexedCollection.nonUniqueIndexedCollection(new ArrayList<>(), new IntegerTransformer());
    }

    private Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    private Collection<String> makeFullCollection() {
        return IndexedCollection.nonUniqueIndexedCollection(
            new ArrayList<>(Arrays.asList(FULL_ELEMENTS)), new IntegerTransformer());
    }

    private Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    private void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    private void resetFull() {
        collection = makeFullCollection();
        confirmed = makeConfirmedFullCollection();
    }

    private Collection<String> getCollection() {
        return collection;
    }

    private Collection<String> getConfirmed() {
        return confirmed;
    }

    /**
     * Asserts that {@code collection} and {@code confirmed} contain the same elements
     * (same size, same multiset of values, regardless of order).
     */
    private void verify() {
        assertEquals(confirmed.size(), collection.size(),
            "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(),
            "Collection isEmpty() result should match confirmed collection's");

        final Object[] confirmedValues = confirmed.toArray();
        final boolean[] matched = new boolean[confirmedValues.length];

        for (final Object element : collection) {
            boolean found = false;
            for (int i = 0; i < confirmedValues.length; i++) {
                if (!matched[i] && Objects.equals(element, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                throw new AssertionError(
                    "Collection has element not present in confirmed: " + element
                    + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }

        for (int i = 0; i < confirmedValues.length; i++) {
            if (!matched[i]) {
                throw new AssertionError(
                    "Collection is missing element that confirmed has: " + confirmedValues[i]
                    + "\nTest: " + collection + "\nReal: " + confirmed);
            }
        }
    }

    /** Clones a Map.Entry so later mutations to the map do not affect the captured key/value. */
    @SuppressWarnings("unchecked")
    private Map.Entry<String, String> cloneMapEntry(final Map.Entry<?, ?> entry) {
        final HashMap<String, String> map = new HashMap<>();
        map.put((String) entry.getKey(), (String) entry.getValue());
        return map.entrySet().iterator().next();
    }

    /**
     * Tests the {@code iterator().remove()} contract on an IndexedCollection:
     * <ul>
     *   <li>remove() before any next() call must throw {@link IllegalStateException}</li>
     *   <li>remove() after hasNext() but before next() must also throw</li>
     *   <li>iterating and removing every element via the iterator empties the collection</li>
     *   <li>a second consecutive remove() without an intervening next() must throw</li>
     * </ul>
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCollectionIteratorRemove() {
        // remove() on a brand-new iterator (no next() called yet) must throw
        resetEmpty();
        assertThrows(IllegalStateException.class,
            () -> getCollection().iterator().remove(),
            "New iterator.remove should raise IllegalState");
        verify();

        // remove() is still illegal after hasNext() if next() has not been called
        final Iterator<String> iterBeforeNext = getCollection().iterator();
        iterBeforeNext.hasNext();
        assertThrows(IllegalStateException.class,
            () -> iterBeforeNext.remove(),
            "New iterator.remove should raise IllegalState even after hasNext");
        verify();

        // Removing every element via the iterator should leave the collection empty
        resetFull();
        int expectedSize = getCollection().size();
        Iterator<String> iter = getCollection().iterator();
        while (iter.hasNext()) {
            Object element = iter.next();
            // Some map-backed iterators reuse their Map.Entry; clone it to preserve the value
            if (element instanceof Map.Entry) {
                element = cloneMapEntry((Map.Entry<?, ?>) element);
            }
            iter.remove();
            // Keep confirmed in sync so verify() can check consistency after each removal
            getConfirmed().remove(element);
            verify();
            expectedSize--;
            assertEquals(expectedSize, getCollection().size(),
                "Collection should shrink by one after iterator.remove");
        }
        assertTrue(getCollection().isEmpty(), "Collection should be empty after iterator purge");

        // Calling remove() twice in a row (without next() in between) must throw
        resetFull();
        iter = getCollection().iterator();
        iter.next();
        iter.remove();
        final Iterator<String> iterAfterRemove = iter;
        assertThrows(IllegalStateException.class,
            () -> iterAfterRemove.remove(),
            "Second iter.remove should raise IllegalState");
    }
}
