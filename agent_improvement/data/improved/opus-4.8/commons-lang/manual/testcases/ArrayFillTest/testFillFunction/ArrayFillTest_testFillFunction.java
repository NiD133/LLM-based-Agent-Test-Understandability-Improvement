package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ArrayFill#fill(Object[], FailableIntFunction)}, the generator-based
 * overload that computes each element from its index.
 */
public class ArrayFillTest_testFillFunction extends AbstractLangTest {

    @Test
    void testFillFunction() throws Exception {
        final FailableIntFunction<?, Exception> nullGenerator = null;

        // A null input array is returned unchanged (as null), regardless of the generator.
        assertNull(ArrayFill.fill(null, nullGenerator));
        assertArrayEquals(null, ArrayFill.fill(null, nullGenerator));

        // An empty input array is returned unchanged (still empty), regardless of the generator.
        assertArrayEquals(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY,
                ArrayFill.fill(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, nullGenerator));
        assertArrayEquals(ArrayUtils.EMPTY_OBJECT_ARRAY,
                ArrayFill.fill(ArrayUtils.EMPTY_OBJECT_ARRAY, nullGenerator));

        // A non-empty array is populated in place: element i becomes generator.apply(i).
        final Integer[] inputArray = new Integer[10];
        final Integer[] filledArray = ArrayFill.fill(inputArray, Integer::valueOf);

        // fill returns the same array instance it was given (fluent style).
        assertSame(inputArray, filledArray);

        // Each element equals its own index, since the generator is Integer::valueOf.
        for (int index = 0; index < inputArray.length; index++) {
            assertEquals(index, inputArray[index].intValue());
        }
    }
}
