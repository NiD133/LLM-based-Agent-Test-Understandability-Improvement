package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookupWithNullDefaultInterpolator {

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
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    private void check(final StringLookup lookup) {
        // Verify system-property lookup resolves a known key
        final String sysValue = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, sysValue);

        // Verify environment-variable lookup returns a value for PATH
        final String envValue = lookup.apply("env:PATH");
        assertNotNull(envValue);

        // Verify date lookup returns today's date in the expected format
        final String dateValue = lookup.apply("date:yyyy-MM-dd");
        assertNotNull(dateValue, "No Date");
        final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(dateValue, today);

        // Verify Java system-info lookups all return non-empty results
        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    @Test
    void testLookupWithNullDefaultInterpolator() {
        // A null default interpolator means prefix-based lookups (sys:, env:, date:, java:)
        // still work via the built-in lookup map, but unrecognised keys return null instead
        // of falling back to a secondary lookup.
        check(new InterpolatorStringLookup((StringLookup) null));
    }
}
