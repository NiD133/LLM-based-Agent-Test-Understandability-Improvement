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

public class EnumUtilsTest_testGetEnumSystemProperty extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetEnumSystemProperty() {
        final String key = getClass().getName();
        System.setProperty(key, Traffic.RED.toString());
        try {
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, key, null));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, "?", Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, null, Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(null, "?", Traffic.RED));
            assertEquals(Traffic.RED, EnumUtils.getEnumSystemProperty(Traffic.class, null, Traffic.RED));
        } finally {
            System.getProperties().remove(key);
        }
    }
}
