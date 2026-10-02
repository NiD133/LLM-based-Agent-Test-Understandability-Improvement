package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

@SuppressWarnings("boxing")
public class IndexedCollectionTest_testCollectionToArray2 {

    private static final int UNORDERED = 0x1;
    private static final String[] FULL_ELEMENTS = { "1", "3", "5", "7", "2", "4", "6" };

    private Collection<String> collection;
    private Collection<String> confirmed;

    protected Collection<String> decorateCollection(final Collection<String> source) {
        return IndexedCollection.nonUniqueIndexedCollection(source, new IntegerTransformer());
    }

    public Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    public Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(FULL_ELEMENTS));
    }

    public Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(FULL_ELEMENTS)));
    }

    public Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    protected int getIterationBehaviour() {
        return 0;
    }

    private static final class IntegerTransformer implements Transformer<String, Integer> {
        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Collection<String> getConfirmed() {
        return confirmed;
    }

    public void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    public void resetFull() {
        collection = makeFullCollection();
        confirmed = makeConfirmedFullCollection();
    }

    public void verify() {
        assertEquals(confirmed.size(), collection.size(), "Collection size should match confirmed collection's");
        assertEquals(confirmed.isEmpty(), collection.isEmpty(), "Collection isEmpty() result should match confirmed collection's");
        assertUnorderedArrayEquals(confirmed.toArray(), collection.toArray(), "Collection contents should match confirmed collection's");
    }

    private static void assertUnorderedArrayEquals(final Object[] expected, final Object[] actual, final String message) {
        assertEquals(expected.length, actual.length, () -> message + ": length");
        final boolean[] matched = new boolean[expected.length];

        NEXT_EXPECTED: for (final Object expectedElement : expected) {
            for (int i = 0; i < actual.length; i++) {
                if (!matched[i] && Objects.equals(expectedElement, actual[i])) {
                    matched[i] = true;
                    continue NEXT_EXPECTED;
                }
            }
            fail(message + ": array 2 does not have object: " + expectedElement);
        }
    }

    /**
     * Tests {@link Collection#toArray(Object[])}.
     */
    @Test
    void testCollectionToArray2() {
        resetEmpty();
        Object[] a = { new Object(), null, null };
        Object[] array = getCollection().toArray(a);
        assertEquals(array, a, "Given array shouldn't shrink");
        assertNull(a[0], "Last element should be set to null");
        verify();

        resetFull();
        assertThrows(ArrayStoreException.class, () -> getCollection().toArray(new Void[0]), "toArray(new Void[0]) should raise ArrayStore");
        verify();
        assertThrows(NullPointerException.class, () -> getCollection().toArray((Object[]) null), "toArray(null) should raise NPE");
        verify();

        array = getCollection().toArray(ArrayUtils.EMPTY_OBJECT_ARRAY);
        a = getCollection().toArray();
        if ((getIterationBehaviour() & UNORDERED) != 0) {
            assertUnorderedArrayEquals(array, a, "toArray(Object[]) and toArray()");
        } else {
            assertEquals(Arrays.asList(array), Arrays.asList(a), "toArrays should be equal");
        }

        final HashSet<Class<?>> elementClasses = new HashSet<>();
        for (final Object element : array) {
            elementClasses.add(element == null ? null : element.getClass());
        }
        if (elementClasses.size() > 1) {
            return;
        }

        Class<?> arrayComponentType = elementClasses.iterator().next();
        if (Map.Entry.class.isAssignableFrom(arrayComponentType)) {
            arrayComponentType = Map.Entry.class;
        }
        a = (Object[]) Array.newInstance(arrayComponentType, 0);
        array = getCollection().toArray(a);
        assertEquals(a.getClass(), array.getClass(), "toArray(Object[]) should return correct array type");
        if ((getIterationBehaviour() & UNORDERED) != 0) {
            assertUnorderedArrayEquals(array, getCollection().toArray(), "type-specific toArray(T[]) and toArray()");
        } else {
            assertEquals(Arrays.asList(array), Arrays.asList(getCollection().toArray()), "type-specific toArrays should be equal");
        }
        verify();
    }
}
