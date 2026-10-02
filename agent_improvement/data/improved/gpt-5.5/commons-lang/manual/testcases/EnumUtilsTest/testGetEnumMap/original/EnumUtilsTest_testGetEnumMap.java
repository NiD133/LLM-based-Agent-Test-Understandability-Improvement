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

public class EnumUtilsTest_testGetEnumMap extends AbstractLangTest {

    private void assertLongArrayEquals(final long[] actual, final long... expected) {
        assertArrayEquals(expected, (long[]) actual);
    }

    @Test
    void testGetEnumMap() {
        final Map<String, Traffic> test = EnumUtils.getEnumMap(Traffic.class);
        final Map<String, Traffic> expected = new HashMap<>();
        expected.put("RED", Traffic.RED);
        expected.put("AMBER", Traffic.AMBER);
        expected.put("GREEN", Traffic.GREEN);
        assertEquals(expected, test, "getEnumMap not created correctly");
        assertEquals(3, test.size());
        assertTrue(test.containsKey("RED"));
        assertEquals(Traffic.RED, test.get("RED"));
        assertTrue(test.containsKey("AMBER"));
        assertEquals(Traffic.AMBER, test.get("AMBER"));
        assertTrue(test.containsKey("GREEN"));
        assertEquals(Traffic.GREEN, test.get("GREEN"));
        assertFalse(test.containsKey("PURPLE"));
    }
}
