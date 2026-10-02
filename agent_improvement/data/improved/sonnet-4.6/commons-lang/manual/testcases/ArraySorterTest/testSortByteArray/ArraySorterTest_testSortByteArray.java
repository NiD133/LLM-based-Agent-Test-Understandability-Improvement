package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortByteArray extends AbstractLangTest {

    @Test
    void testSortByteArraySortsElementsInAscendingOrder() {
        final byte[] unsortedInput = {2, 1};
        final byte[] expectedAscending = {1, 2};
        assertArrayEquals(expectedAscending, ArraySorter.sort(unsortedInput));
    }

    @Test
    void testSortByteArrayReturnsNullForNullInput() {
        assertNull(ArraySorter.sort((byte[]) null));
    }
}
