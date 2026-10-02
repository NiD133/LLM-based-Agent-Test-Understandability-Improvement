package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testNegativeMax {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };
    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Passing a negative {@code max} to the BoundedIterator constructor must throw
     * an {@link IllegalArgumentException} with the message
     * "Max parameter must not be negative."
     */
    @Test
    void testNegativeMax() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> new BoundedIterator<>(testList.iterator(), 3, -1)
        );
        assertEquals("Max parameter must not be negative.", thrown.getMessage());
    }
}
