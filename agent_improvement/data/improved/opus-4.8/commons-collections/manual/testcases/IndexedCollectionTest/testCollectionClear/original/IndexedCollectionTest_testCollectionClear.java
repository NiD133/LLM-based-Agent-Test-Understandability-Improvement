package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.collections4.AbstractObjectTest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionClear {

    protected Collection<String> decorateCollection(final Collection<String> collection) {
        return IndexedCollection.nonUniqueIndexedCollection(collection, new IntegerTransformer());
    }

    protected IndexedCollection<Integer, String> decorateUniqueCollection(final Collection<String> collection) {
        return IndexedCollection.uniqueIndexedCollection(collection, new IntegerTransformer());
    }

    public String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    public String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> makeTestCollection() {
        return decorateCollection(new ArrayList<>());
    }

    public Collection<String> makeUniqueTestCollection() {
        return decorateUniqueCollection(new ArrayList<>());
    }

    protected boolean skipSerializedCanonicalTests() {
        // FIXME: support canonical tests
        return true;
    }

    /**
     * Flag to indicate the collection makes no ordering guarantees for the iterator. If this is not used
     * then the behavior is assumed to be ordered and the output order of the iterator is matched by
     * the toArray method.
     */
    public static final int UNORDERED = 0x1;

    /**
     * Handle the optional exceptions declared by {@link Collection#contains(Object)}
     * @param coll
     * @param element
     */
    protected static void assertNotCollectionContains(final Collection<?> coll, final Object element) {
        try {
            assertFalse(coll.contains(element));
        } catch (final ClassCastException | NullPointerException e) {
            //apparently not
        }
    }

    /**
     * Handle the optional exceptions declared by {@link Collection#containsAll(Collection)}
     * @param coll
     * @param sub
     */
    protected static void assertNotCollectionContainsAll(final Collection<?> coll, final Collection<?> sub) {
        try {
            assertFalse(coll.containsAll(sub));
        } catch (final ClassCastException | NullPointerException e) {
            //apparently not
        }
    }

    /**
     * Handle optional exceptions of {@link Collection#removeAll(Collection)}
     * @param coll
     * @param sub
     */
    protected static void assertNotRemoveAllFromCollection(final Collection<?> coll, final Collection<?> sub) {
        try {
            assertFalse(coll.removeAll(sub));
        } catch (final ClassCastException | NullPointerException e) {
            //apparently not
        }
    }

    /**
     * Handle optional exceptions of {@link Collection#remove(Object)}
     * @param coll
     * @param element
     */
    protected static void assertNotRemoveFromCollection(final Collection<?> coll, final Object element) {
        try {
            assertFalse(coll.remove(element));
        } catch (final ClassCastException | NullPointerException e) {
            //apparently not
        }
    }

    /**
     * Assert the arrays contain the same elements, ignoring the order.
     *
     * <p>Note this does not test the arrays are deeply equal. Array elements are compared
     * using {@link Object#equals(Object)}.
     *
     * @param a1 First array
     * @param a2 Second array
     * @param msg Failure message prefix
     */
    private static void assertUnorderedArrayEquals(final Object[] a1, final Object[] a2, final String msg) {
        assertEquals(a1.length, a2.length, () -> msg + ": length");
        final int size = a1.length;
        // Track values that have been matched once (and only once)
        final boolean[] matched = new boolean[size];
        NEXT_OBJECT: for (final Object o : a1) {
            for (int i = 0; i < size; i++) {
                if (matched[i]) {
                    // skip values already matched
                    continue;
                }
                if (Objects.equals(o, a2[i])) {
                    // values matched
                    matched[i] = true;
                    // continue to the outer loop
                    continue NEXT_OBJECT;
                }
            }
            fail(msg + ": array 2 does not have object: " + o);
        }
    }

    protected static void replaceInt(final byte[] bytes, final int from, final int to) {
        for (int i = 0; i + 4 <= bytes.length; i++) {
            if (((bytes[i] & 0xFF) << 24 | (bytes[i + 1] & 0xFF) << 16 | (bytes[i + 2] & 0xFF) << 8 | bytes[i + 3] & 0xFF) == from) {
                bytes[i] = (byte) (to >>> 24);
                bytes[i + 1] = (byte) (to >>> 16);
                bytes[i + 2] = (byte) (to >>> 8);
                bytes[i + 3] = (byte) to;
                return;
            }
        }
        throw new IllegalStateException("marker not found in stream");
    }

    /**
     * A collection instance that will be used for testing.
     */
    private Collection<E> collection;

    /**
     * Confirmed collection.  This is an instance of a collection that is
     * confirmed to conform exactly to the java.util.Collection contract.
     * Modification operations are tested by performing a mod on your
     * collection, performing the exact same mod on an equivalent confirmed
     * collection, and then calling verify() to make sure your collection
     * still matches the confirmed collection.
     */
    private Collection<E> confirmed;

    /**
     * Specifies whether equal elements in the collection are, in fact,
     * distinguishable with information not readily available.
     * <p>
     * If a particular value is to be removed from the collection, then there is
     * one and only one value that can be removed, even if there are other
     * elements which are equal to it.
     * </p>
     * <p>
     * In most collection cases, elements are not distinguishable (equal is
     * equal), thus this method defaults to return false.  In some cases,
     * however, they are.  For example, the collection returned from the map's
     * values() collection view are backed by the map, so while there may be
     * two values that are equal, their associated keys are not.  Since the
     * keys are distinguishable, the values are.
     * </p>
     * <p>
     * This flag is used to skip some verifications for iterator.remove()
     * where it is impossible to perform an equivalent modification on the
     * confirmed collection because it is not possible to determine which
     * value in the confirmed collection to actually remove.  Tests that
     * override the default (i.e. where equal elements are distinguishable),
     * should provide additional tests on iterator.remove() to make sure the
     * proper elements are removed when remove() is called on the iterator.
     * </p>
     */
    public boolean areEqualElementsDistinguishable() {
        return false;
    }

    /**
     * Creates a new Map Entry that is independent of the first and the map.
     */
    public Map.Entry<E, E> cloneMapEntry(final Map.Entry<E, E> entry) {
        final HashMap<E, E> map = new HashMap<>();
        map.put(entry.getKey(), entry.getValue());
        return map.entrySet().iterator().next();
    }

    public Collection<E> getCollection() {
        return collection;
    }

    public Collection<E> getConfirmed() {
        return confirmed;
    }

    /**
     * Returns an array of objects that are contained in a collection
     * produced by {@link #makeFullCollection()}.
     * <p>
     * Every element in the returned array <em>must</em> be an element in a full collection.
     * </p>
     * <p>
     * The default implementation returns a heterogeneous array of
     * objects with some duplicates. null is added if allowed.
     * Override if you require specific testing elements.  Note that if you
     * override {@link #makeFullCollection()}, you <em>must</em> override
     * this method to reflect the contents of a full collection.
     * <p>
     */
    private E[] __super_getFullElements() {
        if (isNullSupported()) {
            final ArrayList<E> list = new ArrayList<>(Arrays.asList(getFullNonNullElements()));
            list.add(4, null);
            return (E[]) list.toArray();
        }
        return getFullNonNullElements().clone();
    }

    /**
     * Returns a list of elements suitable for return by
     * {@link #getFullElements()}.  The array returned by this method
     * does not include null, but does include a variety of objects
     * of different types.  Override getFullElements to return
     * the results of this method if your collection does not support
     * the null element.
     */
    @SuppressWarnings("unchecked")
    public E[] getFullNonNullElements() {
        return (E[]) new Object[] { StringUtils.EMPTY, "One", Integer.valueOf(2), "Three", Integer.valueOf(4), "One", Double.valueOf(5), Float.valueOf(6), "Seven", "Eight", "Nine", Integer.valueOf(10), Short.valueOf((short) 11), Long.valueOf(12), "Thirteen", "14", "15", Byte.valueOf((byte) 16) };
    }

    /**
     * Returns a list of string elements suitable for return by
     * {@link #getFullElements()}.  Override getFullElements to return
     * the results of this method if your collection does not support
     * heterogeneous elements or the null element.
     */
    public Object[] getFullNonNullStringElements() {
        return new Object[] { "If", "the", "dull", "substance", "of", "my", "flesh", "were", "thought", "Injurious", "distance", "could", "not", "stop", "my", "way" };
    }

    /**
     * Return a flag specifying the iteration behavior of the collection.
     * This is used to change the assertions used by specific tests.
     * The default implementation returns 0 which indicates ordered iteration behavior.
     *
     * @return the iteration behavior
     * @see #UNORDERED
     */
    protected int getIterationBehaviour() {
        return 0;
    }

    /**
     * Returns an array of elements that are <em>not</em> contained in a
     * full collection.  Every element in the returned array must
     * not exist in a collection returned by {@link #makeFullCollection()}.
     * The default implementation returns a heterogeneous array of elements
     * without null.  Note that some of the tests add these elements
     * to an empty or full collection, so if your collection restricts
     * certain kinds of elements, you should override this method.
     */
    private E[] __super_getOtherElements() {
        return getOtherNonNullElements();
    }

    /**
     * Returns the default list of objects returned by
     * {@link #getOtherElements()}.  Includes many objects
     * of different types.
     */
    @SuppressWarnings("unchecked")
    public E[] getOtherNonNullElements() {
        return (E[]) new Object[] { Integer.valueOf(0), Float.valueOf(0), Double.valueOf(0), "Zero", Short.valueOf((short) 0), Byte.valueOf((byte) 0), Long.valueOf(0), Character.valueOf('\u0000'), "0" };
    }

    /**
     * Returns a list of string elements suitable for return by
     * {@link #getOtherElements()}.  Override getOtherElements to return
     * the results of this method if your collection does not support
     * heterogeneous elements or the null element.
     */
    public Object[] getOtherNonNullStringElements() {
        return new Object[] { "For", "then", "despite", /* of */
        "space", "I", "would", "be", "brought", "From", "limits", "far", "remote", "where", "thou", "dost", "stay" };
    }

    /**
     * Returns true if the collections produced by
     * {@link #makeObject()} and {@link #makeFullCollection()}
     * support the {@code add} and {@code addAll}
     * operations.
     * <p>
     * Default implementation returns true.  Override if your collection
     * class does not support add or addAll.
     * </p>
     */
    public boolean isAddSupported() {
        return true;
    }

    /**
     * Returns true to indicate that the collection supports equals() comparisons.
     * This implementation returns false;
     */
    public boolean isEqualsCheckable() {
        return false;
    }

    /**
     * Returns true to indicate that the collection supports fail fast iterators.
     * The default implementation returns true;
     */
    public boolean isFailFastSupported() {
        return false;
    }

    /**
     * Returns true to indicate that the collection supports holding null.
     * The default implementation returns true;
     */
    public boolean isNullSupported() {
        return true;
    }

    /**
     * Returns true if the collections produced by
     * {@link #makeObject()} and {@link #makeFullCollection()}
     * support the {@code remove}, {@code removeAll},
     * {@code retainAll}, {@code clear} and
     * {@code iterator().remove()} methods.
     * Default implementation returns true.  Override if your collection
     * class does not support removal operations.
     */
    public boolean isRemoveSupported() {
        return true;
    }

    /**
     * Returns a confirmed empty collection.
     * For instance, an {@link java.util.ArrayList} for lists or a
     * {@link java.util.HashSet} for sets.
     *
     * @return a confirmed empty collection
     */
    private Collection<E> __super_makeConfirmedCollection();

    /**
     * Returns a confirmed full collection.
     * For instance, an {@link java.util.ArrayList} for lists or a
     * {@link java.util.HashSet} for sets.  The returned collection
     * should contain the elements returned by {@link #getFullElements()}.
     *
     * @return a confirmed full collection
     */
    private Collection<E> __super_makeConfirmedFullCollection();

    /**
     * Returns a full collection to be used for testing.  The collection
     * returned by this method should contain every element returned by
     * {@link #getFullElements()}.  The default implementation, in fact,
     * simply invokes {@code addAll} on an empty collection with
     * the results of {@link #getFullElements()}.  Override this default
     * if your collection doesn't support addAll.
     */
    private Collection<E> __super_makeFullCollection() {
        final Collection<E> c = makeObject();
        c.addAll(Arrays.asList(getFullElements()));
        return c;
    }

    /**
     * Return a new, empty {@link Collection} to be used for testing.
     */
    private Collection<E> __super_makeObject();

    /**
     * Resets the {@link #collection} and {@link #confirmed} fields to empty
     * collections.  Invoke this method before performing a modification
     * test.
     */
    public void resetEmpty() {
        this.setCollection(makeObject());
        this.setConfirmed(makeConfirmedCollection());
    }

    /**
     * Resets the {@link #collection} and {@link #confirmed} fields to full
     * collections.  Invoke this method before performing a modification
     * test.
     */
    public void resetFull() {
        this.setCollection(makeFullCollection());
        this.setConfirmed(makeConfirmedFullCollection());
    }

    /**
     * Sets the collection.
     *
     * @param collection the Collection<E> to set
     */
    public void setCollection(final Collection<E> collection) {
        this.collection = collection;
    }

    /**
     * Sets the confirmed.
     *
     * @param confirmed the Collection<E> to set
     */
    public void setConfirmed(final Collection<E> confirmed) {
        this.confirmed = confirmed;
    }

    /**
     *  Verifies that {@link #collection} and {@link #confirmed} have
     *  identical state.
     */
    public void verify() {
        final int confirmedSize = getConfirmed().size();
        assertEquals(confirmedSize, getCollection().size(), "Collection size should match confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(), "Collection isEmpty() result should match confirmed collection's");
        // verify the collections are the same by attempting to match each
        // object in the collection and confirmed collection.  To account for
        // duplicates and differing orders, each confirmed element is copied
        // into an array and a flag is maintained for each element to determine
        // whether it has been matched once and only once.  If all elements in
        // the confirmed collection are matched once and only once and there
        // aren't any elements left to be matched in the collection,
        // verification is a success.
        // copy each collection value into an array
        final Object[] confirmedValues = new Object[confirmedSize];
        Iterator<E> iter;
        iter = getConfirmed().iterator();
        int pos = 0;
        while (iter.hasNext()) {
            confirmedValues[pos++] = iter.next();
        }
        // allocate an array of boolean flags for tracking values that have
        // been matched once and only once.
        final boolean[] matched = new boolean[confirmedSize];
        // now iterate through the values of the collection and try to match
        // the value with one in the confirmed array.
        iter = getCollection().iterator();
        while (iter.hasNext()) {
            final Object o = iter.next();
            boolean match = false;
            for (int i = 0; i < confirmedSize; i++) {
                if (matched[i]) {
                    // skip values already matched
                    continue;
                }
                if (Objects.equals(o, confirmedValues[i])) {
                    // values matched
                    matched[i] = true;
                    match = true;
                    break;
                }
            }
            // no match found!
            if (!match) {
                fail("Collection should not contain a value that the " + "confirmed collection does not have: " + o + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
        // make sure there aren't any unmatched values
        for (int i = 0; i < confirmedSize; i++) {
            if (!matched[i]) {
                // the collection didn't match all the confirmed values
                fail("Collection should contain all values that are in the confirmed collection" + "\nTest: " + getCollection() + "\nReal: " + getConfirmed());
            }
        }
    }

    /**
     *  Test {@link Collection#clear()}.
     */
    @Test
    void testCollectionClear() {
        if (!isRemoveSupported()) {
            return;
        }
        resetEmpty();
        // just to make sure it doesn't raise anything
        getCollection().clear();
        verify();
        resetFull();
        getCollection().clear();
        getConfirmed().clear();
        verify();
    }
}
