package org.apache.commons.collections4.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.collections4.Transformer;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@code add} / {@code addAll} raise {@link UnsupportedOperationException}
 * on an {@link IndexedCollection} that does not support adding elements.
 *
 * <p>This is the standard {@code AbstractCollectionTest} contract test. It only
 * performs assertions when {@link #isAddSupported()} returns {@code false}.
 * {@link IndexedCollection} <em>does</em> support {@code add}, so
 * {@link #isAddSupported()} returns {@code true} and the test is a guarded
 * no-op: it returns immediately and the assertions below are never executed.</p>
 */
@SuppressWarnings("boxing")
public class IndexedCollectionTest_testUnsupportedAdd {

    /** Maps a numeric {@link String} value onto its {@link Integer} index key. */
    private static final class IntegerTransformer implements Transformer<String, Integer>, Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer transform(final String input) {
            return Integer.valueOf(input);
        }
    }

    /** The decorated collection under test. */
    private Collection<String> collection;

    /**
     * A plain {@link ArrayList} kept in lock-step with {@link #collection}, used by
     * {@link #verify()} as the reference for the expected contents.
     */
    private Collection<String> confirmed;

    private Collection<String> decorateCollection(final Collection<String> coll) {
        return IndexedCollection.nonUniqueIndexedCollection(coll, new IntegerTransformer());
    }

    private String[] getFullElements() {
        return new String[] { "1", "3", "5", "7", "2", "4", "6" };
    }

    private String[] getOtherElements() {
        return new String[] { "9", "88", "678", "87", "98", "78", "99" };
    }

    /** Elements valid for adding to a collection; index 0 is used by the add tests. */
    private String[] getFullNonNullElements() {
        return new String[] { "1", "2", "3", "4", "5", "6", "7" };
    }

    private boolean isAddSupported() {
        // IndexedCollection supports add and addAll.
        return true;
    }

    private Collection<String> makeObject() {
        return decorateCollection(new ArrayList<>());
    }

    private Collection<String> makeFullCollection() {
        return decorateCollection(new ArrayList<>(Arrays.asList(getFullElements())));
    }

    private Collection<String> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    private Collection<String> makeConfirmedFullCollection() {
        return new ArrayList<>(Arrays.asList(getFullElements()));
    }

    /** Resets {@link #collection} and {@link #confirmed} to empty collections. */
    private void resetEmpty() {
        collection = makeObject();
        confirmed = makeConfirmedCollection();
    }

    /** Resets {@link #collection} and {@link #confirmed} to full collections. */
    private void resetFull() {
        collection = makeFullCollection();
        confirmed = makeConfirmedFullCollection();
    }

    private Collection<String> getCollection() {
        return collection;
    }

    private Collection<String> getConfirmed() {
        return confirmed;
    }

    /** Verifies that {@link #collection} holds exactly the same elements as {@link #confirmed}. */
    private void verify() {
        assertEquals(getConfirmed().size(), getCollection().size(),
                "Collection size should match the confirmed collection's");
        assertEquals(getConfirmed().isEmpty(), getCollection().isEmpty(),
                "Collection isEmpty() should match the confirmed collection's");

        final Collection<String> remaining = new ArrayList<>(getConfirmed());
        for (final Object element : getCollection()) {
            if (!remaining.remove(element)) {
                fail("Collection contains an unexpected value: " + element
                        + "\nTest: " + getCollection() + "\nConfirmed: " + getConfirmed());
            }
        }
        if (!remaining.isEmpty()) {
            fail("Collection is missing expected values: " + remaining
                    + "\nTest: " + getCollection() + "\nConfirmed: " + getConfirmed());
        }
    }

    /**
     * If {@link #isAddSupported()} returns {@code false}, asserts that {@code add} and
     * {@code addAll} raise {@link UnsupportedOperationException} on both empty and full
     * collections, leaving the collection unchanged each time.
     */
    @Test
    void testUnsupportedAdd() {
        if (isAddSupported()) {
            return;
        }

        resetEmpty();
        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().add(getFullNonNullElements()[0]),
                "Empty collection should not support add.");
        verify();
        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().addAll(Arrays.asList(getFullElements())),
                "Empty collection should not support addAll.");
        verify();

        resetFull();
        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().add(getFullNonNullElements()[0]),
                "Full collection should not support add.");
        verify();
        assertThrows(UnsupportedOperationException.class,
                () -> getCollection().addAll(Arrays.asList(getOtherElements())),
                "Full collection should not support addAll.");
        verify();
    }
}
