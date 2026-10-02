package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testNegativeOffset {

    private final String[] testArray = { "a", "b", "c", "d", "e", "f", "g" };
    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(testArray);
    }

    /**
     * Verifies that constructing a BoundedIterator with a negative offset
     * throws an IllegalArgumentException with the expected error message.
     */
    @Test
    void testNegativeOffset() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> new BoundedIterator<>(testList.iterator(), -1, 4)
        );
        assertEquals("Offset parameter must not be negative.", thrown.getMessage());
    }
}
