package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#stream(Class)}.
 */
public class EnumUtilsTest_testStream extends AbstractLangTest {

    @Test
    void testStream() {
        // An enum class streams exactly its constants, in declaration order.
        assertEquals(TimeUnit.values().length, EnumUtils.stream(TimeUnit.class).count());
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));

        // A non-enum class yields an empty stream.
        assertEquals(0, EnumUtils.stream(Object.class).count());

        // A null class also yields an empty stream.
        assertEquals(0, EnumUtils.stream(null).count());
    }
}
