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

    private static final String SYSTEM_PROPERTY_KEY = "TestKey";

    private static final String SECOND_SYSTEM_PROPERTY_KEY = "TestKey2";

    private static final String SYSTEM_PROPERTY_VALUE = "TestValue";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @AfterAll
    public static void afterAll() {
        System.clearProperty(SYSTEM_PROPERTY_KEY);
        System.clearProperty(SECOND_SYSTEM_PROPERTY_KEY);
    }

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(SYSTEM_PROPERTY_KEY, SYSTEM_PROPERTY_VALUE);
        System.setProperty(SECOND_SYSTEM_PROPERTY_KEY, SYSTEM_PROPERTY_VALUE);
    }

    private void assertDefaultLookupsResolve(final StringLookup lookup) {
        assertSystemPropertyLookupResolves(lookup);
        assertEnvironmentLookupResolves(lookup);
        assertDateLookupResolvesToToday(lookup);
        assertJavaLookupsResolve(lookup);
    }

    private void assertSystemPropertyLookupResolves(final StringLookup lookup) {
        final String value = lookup.apply("sys:" + SYSTEM_PROPERTY_KEY);
        assertEquals(SYSTEM_PROPERTY_VALUE, value);
    }

    private void assertEnvironmentLookupResolves(final StringLookup lookup) {
        final String value = lookup.apply("env:PATH");
        assertNotNull(value);
    }

    private void assertDateLookupResolvesToToday(final StringLookup lookup) {
        final String value = lookup.apply("date:" + DATE_PATTERN);
        assertNotNull(value, "No Date");
        final SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        final String today = format.format(new Date());
        assertEquals(value, today);
    }

    private void assertJavaLookupsResolve(final StringLookup lookup) {
        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    private void assertLookupNotEmpty(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    @Test
    void testLookupWithDefaultInterpolator() {
        assertDefaultLookupsResolve(new InterpolatorStringLookup());
    }
}
