package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testStream extends AbstractLangTest {

    @Test
    void testStream() {
        assertEquals(7, EnumUtils.stream(TimeUnit.class).count());
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));

        assertEquals(0, EnumUtils.stream(Object.class).count());
        assertEquals(0, EnumUtils.stream(null).count());
    }
}
