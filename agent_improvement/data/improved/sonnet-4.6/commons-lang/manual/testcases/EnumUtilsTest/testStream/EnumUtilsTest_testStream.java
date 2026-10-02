package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testStream extends AbstractLangTest {

    // TimeUnit has 7 constants: NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS
    private static final int TIME_UNIT_COUNT = 7;

    @Test
    void testStream_enumClass_returnsCorrectCount() {
        assertEquals(TIME_UNIT_COUNT, EnumUtils.stream(TimeUnit.class).count());
    }

    @Test
    void testStream_enumClass_returnsAllValuesInOrder() {
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));
    }

    @Test
    void testStream_nonEnumClass_returnsEmptyStream() {
        assertEquals(0, EnumUtils.stream(Object.class).count());
    }

    @Test
    void testStream_nullClass_returnsEmptyStream() {
        assertEquals(0, EnumUtils.stream(null).count());
    }
}
