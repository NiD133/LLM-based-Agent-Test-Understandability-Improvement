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

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionContains {

    /** Transforms a String to its Integer value; used as the index key for {@link IndexedCollection}. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The {@link IndexedCollection} under test. */
    private Collection<String> collection;

    /** A plain {@link ArrayList} mirroring the expected state of {@link #collection}. */
    private Collection<String> confirmed;

    // -----------------------------------------------------------------------
    // Factory helpers
    // -----------------------------------------------------------------------

    protected Collection<String> decorateCollection(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Elements that belong to a "full" collection: single-digit and small integers as strings. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Elements that must NOT appear in a full collection. */
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

    // -----------------------------------------------------------------------
    // State accessors / mutators
    // -----------------------------------------------------------------------

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

    public void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    // -----------------------------------------------------------------------
    // Verification
    // -----------------------------------------------------------------------

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain exactly the same elements
     * (order-independent). Called after read-only operations to ensure they did not mutate state.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
                "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        // Copy confirmed elements into an array so we can track which ones have been matched.
        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confirmedIter = getConfirmed().iterator();
        int pos = 0;
        while (confirmedIter.hasNext()) {
            confirmedValues[pos++] = confirmedIter.next();
        }

        final boolean[] matched = new boolean[confirmedSize];
        final Iterator<String> collectionIter = getCollection().iterator();
        while (collectionIter.hasNext()) {
            final Object o = collectionIter.next();
            boolean found = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(o, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection contains a value not in the confirmed collection: " + o
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection is missing a value that is in the confirmed collection"
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Tests {@link Collection#contains(Object)}.
     *
     * <ul>
     *   <li>An empty collection must not contain any of the full or "other" elements.</li>
     *   <li>A full collection must contain every full element but none of the "other" elements.</li>
     *   <li>After each batch of {@code contains()} calls, {@link #verify()} confirms the
     *       collection was not mutated.</li>
     * </ul>
     */
    @Test
    void testCollectionContains() {
        // --- Empty collection: must not contain full elements ---
        resetEmpty();
        final String[] fullElements = getFullElements();
        for (int i = 0; i < fullElements.length; i++) {
            assertFalse(getCollection().contains(fullElements[i]),
                    "Empty collection shouldn't contain element[" + i + "]");
        }
        verify(); // contains() must not mutate the collection

        // --- Empty collection: must not contain "other" elements ---
        final String[] otherElements = getOtherElements();
        for (int i = 0; i < otherElements.length; i++) {
            assertFalse(getCollection().contains(otherElements[i]),
                    "Empty collection shouldn't contain element[" + i + "]");
        }
        verify();

        // --- Full collection: must contain every full element ---
        resetFull();
        final String[] fullElementsAgain = getFullElements();
        for (int i = 0; i < fullElementsAgain.length; i++) {
            assertTrue(getCollection().contains(fullElementsAgain[i]),
                    "Full collection should contain element[" + i + "]");
        }
        verify();

        // --- Full collection: must not contain "other" elements ---
        resetFull();
        for (final String element : getOtherElements()) {
            assertFalse(getCollection().contains(element),
                    "Full collection shouldn't contain element");
        }
    }
}
