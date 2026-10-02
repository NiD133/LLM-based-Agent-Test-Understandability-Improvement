package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookupWithDefaultInterpolator {

    private static final String TESTKEY = "TestKey";

    private static final String TESTVAL = "TestValue";

    @AfterAll
    public static void afterAll() {
        System.clearProperty(TESTKEY);
    }

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(TESTKEY, TESTVAL);
    }

    private void assertLookupNotEmpty(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value, "Expected non-null value for key: " + key);
        assertFalse(value.isEmpty(), "Expected non-empty value for key: " + key);
    }

    private void assertAllDefaultLookupsWork(final StringLookup lookup) {
        // System property lookup
        String value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value, "System property lookup should return the registered test value");

        // Environment variable lookup
        value = lookup.apply("env:PATH");
        assertNotNull(value, "Environment variable PATH should be resolvable");

        // Date lookup with format
        value = lookup.apply("date:yyyy-MM-dd");
        assertNotNull(value, "Date lookup should return today's date");
        final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(value, today, "Date lookup should match today's formatted date");

        // Java platform information lookups
        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    @Test
    void testLookupWithDefaultInterpolator() {
        assertAllDefaultLookupsWork(new InterpolatorStringLookup());
    }
}
