package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class ArraySorterTest_testSortObjects extends AbstractLangTest {

    @Test
    void testSortObjects_sortsStringsIntoAscendingOrder() {
        // Unsorted input: ["foo", "bar"] should become ["bar", "foo"]
        final String[] unsorted = ArrayUtils.toArray("foo", "bar");
        final String[] copyOfUnsorted = unsorted.clone();

        final String[] expectedSorted = unsorted.clone();
        Arrays.sort(expectedSorted);

        assertArrayEquals(expectedSorted, ArraySorter.sort(copyOfUnsorted));
    }

    @Test
    void testSortObjects_returnsNullWhenInputIsNull() {
        assertNull(ArraySorter.sort((String[]) null));
    }
}
