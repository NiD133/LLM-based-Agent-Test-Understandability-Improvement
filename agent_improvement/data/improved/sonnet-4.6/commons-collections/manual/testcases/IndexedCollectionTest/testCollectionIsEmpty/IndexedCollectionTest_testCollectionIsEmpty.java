package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link IndexedCollection#isEmpty()} correctly reflects whether the
 * collection has elements, and that calling it does not modify the collection state.
 */
public class IndexedCollectionTest_testCollectionIsEmpty {

    // ---- fixture state ----

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain ArrayList used as a reference to cross-check the collection under test. */
    private Collection<String> confirmed;

    // ---- fixture wiring ----

    /** Elements used to populate the "full" collection fixture. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Wraps a raw ArrayList in a non-unique IndexedCollection keyed by integer value. */
    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    /** Returns a new, empty IndexedCollection. */
    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    /** Returns a plain empty ArrayList that mirrors the empty IndexedCollection. */
    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    /** Returns an IndexedCollection pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    /** Returns a plain ArrayList pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    /** Resets both fixtures to empty state before testing an empty collection. */
    public void resetEmpty() {
        this.collection = makeObject();
        this.confirmed  = makeConfirmedCollection();
    }

    /** Resets both fixtures to a full state before testing a populated collection. */
    public void resetFull() {
        this.collection = makeFullCollection();
        this.confirmed  = makeConfirmedFullCollection();
    }

    public Collection<String> getCollection() { return collection; }
    public Collection<String> getConfirmed()  { return confirmed;  }

    // ---- cross-check helper ----

    /**
     * Verifies that {@link #collection} and {@link #confirmed} have identical size
     * and identical elements (order-insensitive), ensuring no operation silently
     * mutated the collection under test.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
            "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
            "Collection isEmpty() result should match confirmed collection's");

        // Collect all confirmed values into an array for element-wise matching.
        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confirmedIter = getConfirmed().iterator();
        for (int pos = 0; confirmedIter.hasNext(); pos++) {
            confirmedValues[pos] = confirmedIter.next();
        }

        // Match each element in the collection under test against a confirmed value
        // (track which confirmed values have already been matched to handle duplicates).
        final boolean[] matched = new boolean[confirmedSize];
        for (final String actual : getCollection()) {
            boolean found = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(actual, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection should not contain a value that the confirmed collection does not have: "
                    + actual + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                    + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    // ---- test ----

    /**
     * Verifies that a freshly created IndexedCollection reports {@code isEmpty() == true},
     * and that a collection containing elements reports {@code isEmpty() == false}.
     * The {@link #verify()} calls confirm that invoking {@code isEmpty()} has no side
     * effects on the collection's contents.
     */
    @Test
    void testCollectionIsEmpty() {
        resetEmpty();
        assertTrue(getCollection().isEmpty(), "New Collection should be empty.");
        verify(); // isEmpty() must not have changed anything

        resetFull();
        assertFalse(getCollection().isEmpty(), "Full collection shouldn't be empty");
        verify(); // isEmpty() must not have changed anything
    }
}
