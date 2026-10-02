package org.apache.commons.collections4.iterators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoundedIteratorTest_testNegativeOffset {

    private static final String[] TEST_ARRAY = { "a", "b", "c", "d", "e", "f", "g" };
    private static final String NEGATIVE_OFFSET_MESSAGE = "Offset parameter must not be negative.";

    private List<String> testList;

    @BeforeEach
    public void setUp() {
        testList = Arrays.asList(TEST_ARRAY);
    }

    /**
     * Test the case if a negative {@code offset} is passed to the
     * constructor. {@link IllegalArgumentException} is expected.
     */
    @Test
    void testNegativeOffset() {
        final IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new BoundedIterator<>(testList.iterator(), -1, 4));

        assertEquals(NEGATIVE_OFFSET_MESSAGE, thrown.getMessage());
    }
}
