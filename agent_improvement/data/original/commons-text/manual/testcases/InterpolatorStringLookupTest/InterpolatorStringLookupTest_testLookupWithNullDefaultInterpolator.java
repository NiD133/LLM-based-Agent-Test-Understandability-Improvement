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
        // System.out.println(key + " = " + value);
    }

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

    @Test
    void testLookupWithNullDefaultInterpolator() {
        check(new InterpolatorStringLookup((StringLookup) null));
    }
}
