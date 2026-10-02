package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionToArray2 {

    /**
     * Transforms a numeric string element into its Integer key for the index.
     */
    static class IntegerTransformer implements Transformer<String, Integer>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.parseInt(input);
        }
    }

    /**
     * Flag indicating the collection makes no ordering guarantees for the iterator.
     * When set, array equality checks are done without regard to order.
     */
    public static final int UNORDERED = 0x1;

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain ArrayList used as the reference ("confirmed") collection. */
    private Collection<String> confirmed;

    // ----- factory helpers -----

    protected Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    /** Numeric string elements that form the full collection. */
    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    /** Returns an empty IndexedCollection wrapping an empty ArrayList. */
    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    /** Returns an IndexedCollection pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    /** Returns an empty ArrayList that mirrors an empty collection state. */
    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    /** Returns an ArrayList pre-populated with {@link #getFullElements()}. */
    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    // ----- state accessors / mutators -----

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

    /** Resets both collections to an empty state before a test step. */
    public void resetEmpty() {
        setCollection(makeObject());
        setConfirmed(makeConfirmedCollection());
    }

    /** Resets both collections to the full state before a test step. */
    public void resetFull() {
        setCollection(makeFullCollection());
        setConfirmed(makeConfirmedFullCollection());
    }

    /**
     * Returns the iteration-order flag. 0 means ordered; {@link #UNORDERED} means unordered.
     * This implementation returns 0 (ordered).
     */
    protected int getIterationBehaviour() {
        return 0;
    }

    // ----- assertion helpers -----

    /**
     * Verifies that {@link #collection} and {@link #confirmed} contain the same elements.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(),
                "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() result should match confirmed collection's");

        // Snapshot the confirmed values so we can match them one-by-one.
        final Object[] confirmedValues = new Object[confirmedSize];
        final Iterator<String> confIter = getConfirmed().iterator();
        int pos = 0;
        while (confIter.hasNext()) {
            confirmedValues[pos++] = confIter.next();
        }

        // Walk the test collection and match each element against a confirmed value.
        final boolean[] matched = new boolean[confirmedSize];
        final Iterator<String> testIter = getCollection().iterator();
        while (testIter.hasNext()) {
            final Object o = testIter.next();
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (!matched[i] && Objects.equals(o, confirmedValues[i])) {
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

        // Ensure every confirmed value was matched exactly once.
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                fail("Collection should contain all values that are in the confirmed collection"
                        + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    /**
     * Asserts that two arrays contain the same elements, ignoring order.
     * Each element in {@code a1} must appear in {@code a2} the same number of times.
     */
    private static void assertUnorderedArrayEquals(final Object[] a1, final Object[] a2,
            final String msg) {
        assertEquals(a1.length, a2.length, () -> msg + ": length");
        final int size = a1.length;
        final boolean[] matched = new boolean[size];
        NEXT_OBJECT: for (final Object o : a1) {
            for (int i = 0; i < size; i++) {
                if (!matched[i] && Objects.equals(o, a2[i])) {
                    matched[i] = true;
                    continue NEXT_OBJECT;
                }
            }
            fail(msg + ": array 2 does not have object: " + o);
        }
    }

    // ----- test -----

    /**
     * Tests {@link Collection#toArray(Object[])} on an IndexedCollection:
     * <ol>
     *   <li>On an empty collection the given array is returned unchanged and
     *       its first slot is set to {@code null}.</li>
     *   <li>An incompatible array type raises {@link ArrayStoreException}.</li>
     *   <li>A {@code null} array argument raises {@link NullPointerException}.</li>
     *   <li>{@code toArray(Object[])} and {@code toArray()} return equivalent elements.</li>
     *   <li>When all elements share a common type, the typed overload preserves the
     *       array component type.</li>
     * </ol>
     */
    @Test
    void testCollectionToArray2() {
        // --- empty collection: given array must be reused; first slot nulled ---
        resetEmpty();
        Object[] a = { new Object(), null, null };
        Object[] array = getCollection().toArray(a);
        assertEquals(array, a, "Given array shouldn't shrink");
        assertNull(a[0], "Last element should be set to null");
        verify();

        // --- full collection: incompatible component type raises ArrayStoreException ---
        resetFull();
        assertThrows(ArrayStoreException.class,
                () -> getCollection().toArray(new Void[0]),
                "toArray(new Void[0]) should raise ArrayStore");
        verify();

        // --- null argument raises NullPointerException ---
        // Casting to Object[] is required for compilation on Java 11.
        assertThrows(NullPointerException.class,
                () -> getCollection().toArray((Object[]) null),
                "toArray(null) should raise NPE");
        verify();

        // --- toArray(Object[]) and toArray() must return the same elements ---
        array = getCollection().toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
        a = getCollection().toArray();
        if ((getIterationBehaviour() & UNORDERED) != 0) {
            assertUnorderedArrayEquals(array, a, "toArray(Object[]) and toArray()");
        } else {
            assertEquals(Arrays.asList(array), Arrays.asList(a), "toArrays should be equal");
        }

        // --- typed toArray(T[]) must preserve the array component type ---
        // Collect all distinct element classes to find a shared type.
        final HashSet<Class<?>> classes = new HashSet<>();
        for (final Object element : array) {
            classes.add(element == null ? null : element.getClass());
        }
        if (classes.size() > 1) {
            // No single common class; skip typed-array check.
            return;
        }
        Class<?> cl = classes.iterator().next();
        if (Map.Entry.class.isAssignableFrom(cl)) {
            // Normalize Map.Entry subtypes so the typed array works for decorated map views.
            cl = Map.Entry.class;
        }
        a = (Object[]) Array.newInstance(cl, 0);
        array = getCollection().toArray(a);
        assertEquals(a.getClass(), array.getClass(),
                "toArray(Object[]) should return correct array type");
        if ((getIterationBehaviour() & UNORDERED) != 0) {
            assertUnorderedArrayEquals(array, getCollection().toArray(),
                    "type-specific toArray(T[]) and toArray()");
        } else {
            assertEquals(Arrays.asList(array), Arrays.asList(getCollection().toArray()),
                    "type-specific toArrays should be equal");
        }
        verify();
    }
}
