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

    private void check(final StringLookup lookup) {
        final String systemPropertyValue = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, systemPropertyValue);

        final String pathEnvironmentValue = lookup.apply("env:PATH");
        assertNotNull(pathEnvironmentValue);

        final String dateValue = lookup.apply("date:yyyy-MM-dd");
        assertNotNull(dateValue, "No Date");
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final String today = format.format(new Date());
        assertEquals(dateValue, today);

        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    @Test
    void testLookupWithNullDefaultInterpolator() {
        check(new InterpolatorStringLookup((StringLookup) null));
    }
}
