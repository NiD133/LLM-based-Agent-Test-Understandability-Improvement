package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testRemoveWithoutCallingNext {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Verifies that calling {@code remove()} on a {@link BoundedIterator} before any call
     * to {@code next()} throws an {@link IllegalStateException} with the message
     * "remove() cannot be called before calling next()".
     *
     * <p>This tests the precondition guard in {@code BoundedIterator.remove()}: even when
     * an offset is supplied (which internally advances the underlying iterator during
     * construction), the caller must still invoke {@code next()} at least once before
     * {@code remove()} is permitted.</p>
     */
    @Test
    void testRemoveWithoutCallingNext() {
        // Use a mutable copy so the underlying ArrayList iterator supports remove()
        final List<String> testListCopy = new ArrayList<>(testList);
        final Iterator<String> iter = new BoundedIterator<>(testListCopy.iterator(), 1, 5);

        final IllegalStateException thrown = assertThrows(
                IllegalStateException.class,
                () -> iter.remove());

        assertEquals("remove() cannot be called before calling next()", thrown.getMessage());
    }
}
