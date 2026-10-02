package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookup {

    private static final String TESTKEY = "TestKey";

    private static final String TESTKEY2 = "TestKey2";

    private static final String TESTVAL = "TestValue";

    @AfterAll
    public static void afterAll() {
        System.clearProperty(TESTKEY);
        System.clearProperty(TESTKEY2);
    }

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(TESTKEY, TESTVAL);
        System.setProperty(TESTKEY2, TESTVAL);
    }

    private void assertLookupNotEmpty(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    /**
     * Verifies that the given lookup resolves system properties, environment variables,
     * date expressions, and Java system info keys correctly.
     */
    private void check(final StringLookup lookup) {
        String value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value);
        value = lookup.apply("env:PATH");
        assertNotNull(value);
        value = lookup.apply("date:yyyy-MM-dd");
        assertNotNull(value, "No Date");
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String today = format.format(new Date());
        assertEquals(value, today);
        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    /**
     * Verifies that {@link InterpolatorStringLookup} correctly delegates to the
     * default map lookup (no prefix), the "ctx:" prefix, and the "sys:" prefix,
     * and returns null for keys that cannot be resolved.
     */
    @Test
    void testLookup() {
        // Arrange: build an interpolator backed by a map containing TESTKEY -> TESTVAL.
        // The map lookup acts as the default (unprefixed) resolver.
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);
        final StringLookup lookup = new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));

        // Direct (unprefixed) lookup delegates to the default map lookup.
        String value = lookup.apply(TESTKEY);
        assertEquals(TESTVAL, value);

        // The "ctx:" prefix also routes to the default map lookup.
        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);

        // The "sys:" prefix resolves JVM system properties (set in @BeforeAll).
        value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value);

        // A key that matches no registered lookup or default lookup returns null.
        value = lookup.apply("BadKey");
        assertNull(value);

        // Confirm the "ctx:" lookup remains functional after a failed lookup.
        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);
    }
}
