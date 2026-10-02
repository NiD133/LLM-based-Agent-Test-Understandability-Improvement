package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testStream extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testStream() {
        assertEquals(7, EnumUtils.stream(TimeUnit.class).count());
        assertArrayEquals(TimeUnit.values(), EnumUtils.stream(TimeUnit.class).toArray(TimeUnit[]::new));
        assertEquals(0, EnumUtils.stream(Object.class).count());
        assertEquals(0, EnumUtils.stream(null).count());
    }
}
