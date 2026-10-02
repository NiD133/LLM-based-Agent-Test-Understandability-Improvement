package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionAddAll {

    /** Transforms a numeric string to its Integer key for the index. */
    static class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.parseInt(input);
        }
    }

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain ArrayList used as an authoritative reference for expected state. */
    private Collection<String> confirmed;

    // -------------------------------------------------------------------------
    // Factory helpers
    // -------------------------------------------------------------------------

    protected Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    /** Elements present in a "full" collection. All parse cleanly as integers. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Elements absent from a full collection. Used to test additions to a full collection. */
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

    // -------------------------------------------------------------------------
    // State management
    // -------------------------------------------------------------------------

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

    /** Resets both the test collection and the confirmed reference to empty state. */
    public void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    /** Resets both the test collection and the confirmed reference to full state. */
    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    public boolean isAddSupported() {
        return true;
    }

    // -------------------------------------------------------------------------
    // Verification
    // -------------------------------------------------------------------------

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly
     * the same elements (order-independent).
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
                fail("Collection should not contain a value that the confirmed collection does not have: "
                        + o + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------

    /**
     * Tests {@link Collection#addAll(Collection)} across three scenarios:
     * adding to an empty collection, adding distinct elements to a full collection,
     * and adding duplicate elements to a full collection.
     */
    @Test
    public void testCollectionAddAll() {
        if (!isAddSupported()) {
            return;
        }

        // Scenario 1: addAll on an empty collection — must return true and contain every added element
        resetEmpty();
        String[] elements = getFullElements();
        boolean changed = getCollection().addAll(Arrays.asList(elements));
        getConfirmed().addAll(Arrays.asList(elements));
        verify();
        assertTrue(changed, "Empty collection should change after addAll");
        for (final String element : elements) {
            assertTrue(getCollection().contains(element), "Collection should contain added element");
        }

        // Scenario 2: addAll of new (non-duplicate) elements on a full collection — size must grow by exactly elements.length
        resetFull();
        int sizeBefore = getCollection().size();
        elements = getOtherElements();
        changed = getCollection().addAll(Arrays.asList(elements));
        getConfirmed().addAll(Arrays.asList(elements));
        verify();
        assertTrue(changed, "Full collection should change after addAll");
        for (final String element : elements) {
            assertTrue(getCollection().contains(element), "Full collection should contain added element");
        }
        assertEquals(sizeBefore + elements.length, getCollection().size(), "Size should increase after addAll");

        // Scenario 3: addAll of already-present elements — return value must agree with any size change
        resetFull();
        sizeBefore = getCollection().size();
        changed = getCollection().addAll(Arrays.asList(getFullElements()));
        getConfirmed().addAll(Arrays.asList(getFullElements()));
        verify();
        if (changed) {
            assertTrue(sizeBefore < getCollection().size(), "Size should increase if addAll returns true");
        } else {
            assertEquals(sizeBefore, getCollection().size(), "Size should not change if addAll returns false");
        }
    }
}
