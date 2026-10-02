package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link InterpolatorStringLookup} created with a {@code null} default lookup
 * still resolves all of the built-in (default) prefixed lookups, e.g. {@code sys:}, {@code env:},
 * {@code date:} and the various {@code java:} keys.
 */
public class InterpolatorStringLookupTest_testLookupWithNullDefaultInterpolator {

    /** Name of a system property set up for the {@code sys:} lookup assertion. */
    private static final String SYSTEM_PROPERTY_KEY = "TestKey";

    /** A second system property; present only to mirror the original test fixture. */
    private static final String SYSTEM_PROPERTY_KEY_2 = "TestKey2";

    /** Value shared by both system properties. */
    private static final String SYSTEM_PROPERTY_VALUE = "TestValue";

    @BeforeAll
    public static void setUpSystemProperties() {
        System.setProperty(SYSTEM_PROPERTY_KEY, SYSTEM_PROPERTY_VALUE);
        System.setProperty(SYSTEM_PROPERTY_KEY_2, SYSTEM_PROPERTY_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(SYSTEM_PROPERTY_KEY);
        System.clearProperty(SYSTEM_PROPERTY_KEY_2);
    }

    @Test
    void testLookupWithNullDefaultInterpolator() {
        final StringLookup lookup = new InterpolatorStringLookup((StringLookup) null);

        assertDefaultLookupsResolve(lookup);
    }

    /**
     * Asserts that the given lookup resolves every default prefixed key as expected.
     */
    private void assertDefaultLookupsResolve(final StringLookup lookup) {
        // sys: resolves the system property set up above.
        assertEquals(SYSTEM_PROPERTY_VALUE, lookup.apply("sys:" + SYSTEM_PROPERTY_KEY));

        // env: resolves an environment variable that is expected to be present.
        assertNotNull(lookup.apply("env:PATH"));

        // date: formats the current date using the supplied pattern.
        final String expectedToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(expectedToday, lookup.apply("date:yyyy-MM-dd"), "No Date");

        // java: resolves a range of non-empty runtime descriptions.
        assertNonEmptyLookup(lookup, "java:version");
        assertNonEmptyLookup(lookup, "java:runtime");
        assertNonEmptyLookup(lookup, "java:vm");
        assertNonEmptyLookup(lookup, "java:os");
        assertNonEmptyLookup(lookup, "java:locale");
        assertNonEmptyLookup(lookup, "java:hardware");
    }

    /**
     * Asserts that looking up the given key yields a non-null, non-empty value.
     */
    private void assertNonEmptyLookup(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}
