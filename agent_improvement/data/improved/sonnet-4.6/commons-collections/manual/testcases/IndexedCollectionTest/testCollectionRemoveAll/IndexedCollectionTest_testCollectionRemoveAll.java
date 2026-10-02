package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

public class IndexedCollectionTest_testCollectionRemoveAll {

    /** Transforms a numeric String (e.g. "1") into its Integer value for use as an index key. */
    static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    private Collection<String> collection;
    private Collection<String> confirmed;

    // --- Collection factory helpers ---

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // --- State accessors and mutators ---

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

    public boolean isRemoveSupported() {
        return true;
    }

    /** Resets both the test collection and the reference collection to empty. */
    public void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    /** Resets both the test collection and the reference collection to their full contents. */
    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    /**
     * Verifies that {@link #collection} and {@link #confirmed} contain identical elements
     * (order-independent). Fails with a descriptive message if they differ.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
            "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
            "Collection isEmpty() result should match confirmed collection's");

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
                if (!matched[i] && Objects.equals(o, confirmedValues[i])) {
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

    // --- Test ---

    /**
     * Tests {@link Collection#removeAll(Collection)} against an {@link IndexedCollection},
     * covering: empty input, unrelated elements, full-set removal, and partial-subset removal.
     */
    @Test
    void testCollectionRemoveAll() {
        if (!isRemoveSupported()) {
            return;
        }

        // removeAll on an empty collection always returns false
        resetEmpty();
        assertFalse(getCollection().removeAll(Collections.EMPTY_SET),
            "Empty collection removeAll should return false for empty input");
        verify();
        assertFalse(getCollection().removeAll(new ArrayList<>(getCollection())),
            "Empty collection removeAll should return false for nonempty input");
        verify();

        // removeAll on a full collection with unrelated elements returns false and leaves it unchanged
        resetFull();
        assertFalse(getCollection().removeAll(Collections.EMPTY_SET),
            "Full collection removeAll should return false for empty input");
        verify();
        assertFalse(getCollection().removeAll(Arrays.asList(getOtherElements())),
            "Full collection removeAll should return false for other elements");
        verify();

        // removeAll with all current elements empties the collection
        assertTrue(getCollection().removeAll(new HashSet<>(getCollection())),
            "Full collection removeAll should return true for full elements");
        getConfirmed().removeAll(new HashSet<>(getConfirmed()));
        verify();

        // removeAll with a subset shrinks the collection and removes exactly those elements
        resetFull();
        final int size = getCollection().size();
        final int min = getFullElements().length < 4 ? 0 : 2;
        final int max = getFullElements().length == 1 ? 1 :
                        getFullElements().length <= 5 ? getFullElements().length - 1 : 5;
        final Collection<String> subset = Arrays.asList(getFullElements()).subList(min, max);
        assertTrue(getCollection().removeAll(subset), "Full collection removeAll should work");
        getConfirmed().removeAll(subset);
        verify();
        assertTrue(getCollection().size() < size, "Collection should shrink after removeAll");
        for (final String element : subset) {
            assertFalse(getCollection().contains(element), "Collection shouldn't contain removed element");
        }
    }
}
