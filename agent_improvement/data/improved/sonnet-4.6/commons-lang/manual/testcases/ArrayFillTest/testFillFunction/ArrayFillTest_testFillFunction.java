package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.apache.commons.lang3.function.FailableIntFunction;
import org.junit.jupiter.api.Test;

public class ArrayFillTest_testFillFunction extends AbstractLangTest {

    private static final FailableIntFunction<?, Exception> NULL_GENERATOR = null;

    @Test
    void testFillFunction_nullArrayReturnsNull() throws Exception {
        assertNull(ArrayFill.fill(null, NULL_GENERATOR));
    }

    @Test
    void testFillFunction_nullArrayEqualsNullArray() throws Exception {
        assertArrayEquals(null, ArrayFill.fill(null, NULL_GENERATOR));
    }

    @Test
    void testFillFunction_emptyBooleanObjectArrayIsUnchanged() throws Exception {
        assertArrayEquals(
            ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY,
            ArrayFill.fill(ArrayUtils.EMPTY_BOOLEAN_OBJECT_ARRAY, NULL_GENERATOR));
    }

    @Test
    void testFillFunction_emptyObjectArrayIsUnchanged() throws Exception {
        assertArrayEquals(
            ArrayUtils.EMPTY_OBJECT_ARRAY,
            ArrayFill.fill(ArrayUtils.EMPTY_OBJECT_ARRAY, NULL_GENERATOR));
    }

    @Test
    void testFillFunction_returnsSameArrayInstance() throws Exception {
        final Integer[] array = new Integer[10];
        final Integer[] filledArray = ArrayFill.fill(array, Integer::valueOf);
        assertSame(array, filledArray);
    }

    @Test
    void testFillFunction_fillsEachElementWithItsIndex() throws Exception {
        final Integer[] array = new Integer[10];
        ArrayFill.fill(array, Integer::valueOf);
        for (int i = 0; i < array.length; i++) {
            assertEquals(i, array[i].intValue());
        }
    }
}
