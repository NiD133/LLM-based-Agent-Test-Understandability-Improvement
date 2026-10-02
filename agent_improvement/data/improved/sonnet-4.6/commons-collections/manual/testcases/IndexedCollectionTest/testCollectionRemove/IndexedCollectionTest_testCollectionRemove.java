package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionRemove {

    /** Transforms a String to its Integer value, used as the index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain ArrayList that mirrors the expected state of {@code collection} for verification. */
    private Collection<String> confirmed;

    // --- Factory helpers ---

    protected Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    /** Creates an empty IndexedCollection for testing. */
    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    /** Creates an IndexedCollection pre-populated with all full elements. */
    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    /** Creates an empty ArrayList used as the reference/confirmed collection. */
    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    /** Creates an ArrayList pre-populated with all full elements, used as the confirmed collection. */
    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // --- Accessors ---

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public void setCollection(final Collection<String> collection) {
        this.collection = collection;
    }

    public void setConfirmed(final Collection<String> confirmed) {
        this.confirmed = confirmed;
    }

    /** Resets both collections to an empty state. */
    public void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    /** Resets both collections to a full state containing {@link #getFullElements()}. */
    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    public boolean isRemoveSupported() {
        return true;
    }

    /**
     * Returns false: equal elements in this collection are not distinguishable,
     * so the confirmed collection can be updated by removing any matching element.
     */
    public boolean areEqualElementsDistinguishable() {
        return false;
    }

    /**
     * Verifies that {@code collection} and {@code confirmed} contain the same elements,
     * independent of iteration order.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
                "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        // Copy confirmed elements into an array and track which have been matched.
        final Object[] confirmedValues = new Object[confirmedSize];
        Iterator<String> iter = getConfirmed().iterator();
        int pos = 0;
        while (iter.hasNext()) {
            confirmedValues[pos++] = iter.next();
        }

        final boolean[] matched = new boolean[confirmedSize];
        iter = getCollection().iterator();
        while (iter.hasNext()) {
            final Object o = iter.next();
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (matched[i]) {
                    continue;
                }
                if (Objects.equals(o, confirmedValues[i])) {
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            if (!match) {
                fail("Collection should not contain a value that the confirmed collection does not have: " + o
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }

        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    /**
     * Tests {@link Collection#remove(Object)}.
     * <ol>
     *   <li>Removing a full element from an empty collection returns {@code false}.</li>
     *   <li>Removing an "other" element (not in the full set) from a full collection returns {@code false}.</li>
     *   <li>Removing each present element from a full collection returns {@code true} and shrinks
     *       the collection size by exactly one.</li>
     * </ol>
     */
    @Test
    void testCollectionRemove() {
        if (!isRemoveSupported()) {
            return;
        }

        // Case 1: removing elements from an empty collection always returns false
        resetEmpty();
        for (final String element : getFullElements()) {
            assertFalse(getCollection().remove(element), "Shouldn't remove nonexistent element");
            verify();
        }

        // Case 2: removing elements not present in a full collection returns false
        resetFull();
        for (final String element : getOtherElements()) {
            assertFalse(getCollection().remove(element), "Shouldn't remove nonexistent other element");
            verify();
        }

        // Case 3: removing each present element returns true and shrinks the collection by one
        final int size = getCollection().size();
        for (final String element : getFullElements()) {
            resetFull();
            assertTrue(getCollection().remove(element),
                    "Collection should remove extant element: " + element);
            // When equal elements are indistinguishable, mirror the removal on the confirmed
            // collection and verify both collections remain consistent.
            if (!areEqualElementsDistinguishable()) {
                getConfirmed().remove(element);
                verify();
            }
            assertEquals(size - 1, getCollection().size(), "Collection should shrink after remove");
        }
    }
}
