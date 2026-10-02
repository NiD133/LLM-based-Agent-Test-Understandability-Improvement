package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that an {@link IndexedCollection} iterator raises
 * {@link ConcurrentModificationException} when the collection is structurally
 * modified while an iterator is open.
 *
 * <p>The test is gated by {@link #isFailFastSupported()}, which returns
 * {@code false} for this decorator, so the test body is intentionally skipped
 * at runtime. The code is kept fully compilable to remain consistent with the
 * AbstractCollectionTest contract.
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionIteratorFailFast {

    // -----------------------------------------------------------------------
    // Key transformer: converts a numeric String to its Integer key
    // -----------------------------------------------------------------------

    private static final class IntegerTransformer
            implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 809439581555072949L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    // -----------------------------------------------------------------------
    // Shared state: the collection under test and a reference copy
    // -----------------------------------------------------------------------

    private Collection<String> collection;
    private Collection<String> confirmed;

    // -----------------------------------------------------------------------
    // Factory helpers
    // -----------------------------------------------------------------------

    protected Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // -----------------------------------------------------------------------
    // State accessors and reset
    // -----------------------------------------------------------------------

    public Collection<String> getCollection() { return collection; }
    public Collection<String> getConfirmed()  { return confirmed; }

    /** Resets both the decorated collection and the reference copy to full state. */
    public void resetFull() {
        this.collection = makeFullCollection();
        this.confirmed  = makeConfirmedFullCollection();
    }

    // -----------------------------------------------------------------------
    // Feature flags
    // -----------------------------------------------------------------------

    public boolean isAddSupported() { return true; }

    public boolean isRemoveSupported() { return true; }

    /**
     * Returns {@code false}: {@link IndexedCollection} does not guarantee
     * fail-fast iteration, so {@link #testCollectionIteratorFailFast()} is
     * skipped at runtime.
     */
    public boolean isFailFastSupported() { return false; }

    // -----------------------------------------------------------------------
    // Verification helper
    // -----------------------------------------------------------------------

    /**
     * Asserts that {@link #collection} and {@link #confirmed} contain the same
     * elements (order-independent, duplicates counted).
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
                "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        // Snapshot confirmed values for element-by-element comparison
        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confirmedIter = getConfirmed().iterator();
        for (int pos = 0; confirmedIter.hasNext(); pos++) {
            confirmedValues[pos] = confirmedIter.next();
        }

        // Each element in the test collection must match exactly one confirmed value
        final boolean[] matched = new boolean[confirmedSize];
        for (final String element : getCollection()) {
            boolean found = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(element, confirmedValues[i])) {
                    matched[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("Collection contains an element absent from confirmed: " + element
                        + "\nTest:      " + getCollection()
                        + "\nConfirmed: " + getConfirmed());
            }
        }

        // Every confirmed value must have been matched
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection is missing an element present in confirmed"
                        + "\nTest:      " + getCollection()
                        + "\nConfirmed: " + getConfirmed());
            }
        }
    }

    // -----------------------------------------------------------------------
    // Test
    // -----------------------------------------------------------------------

    /**
     * Verifies that an iterator obtained before any structural modification
     * raises {@link ConcurrentModificationException} on the next call to
     * {@link Iterator#next()}.
     *
     * <p>Each sub-scenario follows the same pattern:
     * <ol>
     *   <li>Call {@link #resetFull()} to obtain a fresh full collection.</li>
     *   <li>Capture an open iterator <em>before</em> the modification.</li>
     *   <li>Apply the structural modification.</li>
     *   <li>Assert that {@link Iterator#next()} throws.</li>
     * </ol>
     */
    @Test
    void testCollectionIteratorFailFast() {
        if (!isFailFastSupported()) {
            return;
        }

        // --- add() and addAll() must invalidate an open iterator ---
        if (isAddSupported()) {
            resetFull();
            final Iterator<String> iterBeforeAdd = getCollection().iterator();
            final String extraElement = getOtherElements()[0];
            getCollection().add(extraElement);
            getConfirmed().add(extraElement);
            assertThrows(ConcurrentModificationException.class,
                    iterBeforeAdd::next,
                    "next() after add() must raise ConcurrentModificationException");
            verify();

            resetFull();
            final Iterator<String> iterBeforeAddAll = getCollection().iterator();
            getCollection().addAll(Arrays.asList(getOtherElements()));
            getConfirmed().addAll(Arrays.asList(getOtherElements()));
            assertThrows(ConcurrentModificationException.class,
                    iterBeforeAddAll::next,
                    "next() after addAll() must raise ConcurrentModificationException");
            verify();
        }

        if (!isRemoveSupported()) {
            return;
        }

        // --- clear() must invalidate an open iterator ---
        resetFull();
        final Iterator<String> iterBeforeClear = getCollection().iterator();
        getCollection().clear();
        try {
            iterBeforeClear.next();
            fail("next() after clear() should raise ConcurrentModificationException");
        } catch (final ConcurrentModificationException | NoSuchElementException expected) {
            // Both are acceptable per the Collection spec after clear()
        }

        // --- remove(element) must invalidate an open iterator ---
        resetFull();
        final Iterator<String> iterBeforeRemove = getCollection().iterator();
        getCollection().remove(getFullElements()[0]);
        assertThrows(ConcurrentModificationException.class,
                iterBeforeRemove::next,
                "next() after remove() must raise ConcurrentModificationException");

        // --- removeIf(predicate) must invalidate an open iterator ---
        resetFull();
        final Iterator<String> iterBeforeRemoveIf = getCollection().iterator();
        getCollection().removeIf(e -> false);
        assertThrows(ConcurrentModificationException.class,
                iterBeforeRemoveIf::next,
                "next() after removeIf() must raise ConcurrentModificationException");

        // --- removeAll(collection) must invalidate an open iterator ---
        resetFull();
        final Iterator<String> iterBeforeRemoveAll = getCollection().iterator();
        final List<String> removeAllSublist = Arrays.asList(getFullElements()).subList(2, 5);
        getCollection().removeAll(removeAllSublist);
        assertThrows(ConcurrentModificationException.class,
                iterBeforeRemoveAll::next,
                "next() after removeAll() must raise ConcurrentModificationException");

        // --- retainAll(collection) must invalidate an open iterator ---
        resetFull();
        final Iterator<String> iterBeforeRetainAll = getCollection().iterator();
        final List<String> retainAllSublist = Arrays.asList(getFullElements()).subList(2, 5);
        getCollection().retainAll(retainAllSublist);
        assertThrows(ConcurrentModificationException.class,
                iterBeforeRetainAll::next,
                "next() after retainAll() must raise ConcurrentModificationException");
    }
}
