package org.apache.commons.collections4.collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Collection#toArray(Object[])} on an {@link IndexedCollection}.
 */
public class IndexedCollectionTest_testCollectionToArray2 {

    /** Indexes the collection by parsing each String element into an Integer key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** Elements used to build a "full" collection. All parse as distinct integers. */
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    /** The IndexedCollection under test. */
    private Collection<String> collection;

    /** A plain collection that mirrors exactly what {@link #collection} should contain. */
    private Collection<String> confirmed;

    /** Wraps the given backing list in a non-unique IndexedCollection keyed by integer value. */
    private static IndexedCollection<Integer, String> indexed(final Collection<String> backing) {
        return IndexedCollection.nonUniqueIndexedCollection(backing, new IntegerTransformer());
    }

    /** Resets the collection under test (and its mirror) to an empty state. */
    private void resetEmpty() {
        collection = indexed(new ArrayList<>());
        confirmed = new ArrayList<>();
    }

    /** Resets the collection under test (and its mirror) to hold {@link #FULL_ELEMENTS}. */
    private void resetFull() {
        collection = indexed(new ArrayList<>(asList(FULL_ELEMENTS)));
        confirmed = new ArrayList<>(asList(FULL_ELEMENTS));
    }

    /**
     * Verifies the collection under test still holds exactly the confirmed elements.
     * The IndexedCollection decorates a list, so iteration order matches the mirror.
     */
    private void verify() {
        assertEquals(new ArrayList<>(confirmed), new ArrayList<>(collection),
                "indexed collection should still match the confirmed collection");
    }

    @Test
    void testCollectionToArray2() {
        // For an empty collection, toArray(array) reuses the supplied array and
        // sets the element just past the contents (here, index 0) to null.
        resetEmpty();
        final Object[] suppliedArray = { new Object(), null, null };
        final Object[] returnedArray = collection.toArray(suppliedArray);
        assertEquals(suppliedArray, returnedArray, "given array shouldn't shrink");
        assertNull(suppliedArray[0], "first slot should be cleared to null");
        verify();

        // Switch to a full collection of String elements.
        resetFull();

        // toArray(T[]) must reject an array whose component type cannot hold the elements.
        assertThrows(ArrayStoreException.class,
                () -> collection.toArray(new Void[0]),
                "toArray(new Void[0]) should raise ArrayStoreException");
        verify();

        // toArray(null) must raise NPE. Cast to Object[] so this compiles on Java 11.
        assertThrows(NullPointerException.class,
                () -> collection.toArray((Object[]) null),
                "toArray(null) should raise NullPointerException");
        verify();

        // toArray(emptyTypedArray) and toArray() must yield equal, equally ordered results.
        final Object[] fromTypedCall = collection.toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
        final Object[] fromUntypedCall = collection.toArray();
        assertEquals(asList(fromTypedCall), asList(fromUntypedCall), "toArray results should be equal");

        // Every element is a String, so all share a single runtime class.
        final HashSet<Class<?>> elementClasses = new HashSet<>();
        for (final Object element : fromTypedCall) {
            elementClasses.add(element == null ? null : element.getClass());
        }
        assertEquals(1, elementClasses.size(), "all elements should share one runtime class");
        final Class<?> elementClass = elementClasses.iterator().next();

        // toArray(T[]) should return an array whose component type is exactly that class.
        final Object[] typedTarget = (Object[]) Array.newInstance(elementClass, 0);
        final Object[] typedResult = collection.toArray(typedTarget);
        assertEquals(typedTarget.getClass(), typedResult.getClass(),
                "toArray(T[]) should return an array of the requested type");
        assertEquals(asList(typedResult), asList(collection.toArray()),
                "type-specific toArray results should be equal");
        verify();
    }
}
