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
 * Verifies that a default {@link InterpolatorStringLookup} can resolve values through each of the
 * prefixed lookups that are registered by default (system properties, environment variables, the
 * current date and various {@code java:*} runtime details).
 */
public class InterpolatorStringLookupTest_testLookupWithDefaultInterpolator {

    /** Name of the system property the test registers and then reads back via the "sys:" prefix. */
    private static final String SYSTEM_PROPERTY_NAME = "TestKey";

    /** A second registered system property, kept to mirror the original fixture setup. */
    private static final String SECOND_SYSTEM_PROPERTY_NAME = "TestKey2";

    /** Value stored under both registered system properties. */
    private static final String SYSTEM_PROPERTY_VALUE = "TestValue";

    @BeforeAll
    public static void registerSystemProperties() {
        System.setProperty(SYSTEM_PROPERTY_NAME, SYSTEM_PROPERTY_VALUE);
        System.setProperty(SECOND_SYSTEM_PROPERTY_NAME, SYSTEM_PROPERTY_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(SYSTEM_PROPERTY_NAME);
        System.clearProperty(SECOND_SYSTEM_PROPERTY_NAME);
    }

    @Test
    void testLookupWithDefaultInterpolator() {
        final StringLookup interpolator = new InterpolatorStringLookup();

        assertSystemPropertyLookup(interpolator);
        assertEnvironmentLookup(interpolator);
        assertDateLookup(interpolator);
        assertJavaRuntimeLookups(interpolator);
    }

    /** The "sys:" prefix should resolve the system property registered in {@link #registerSystemProperties()}. */
    private void assertSystemPropertyLookup(final StringLookup interpolator) {
        assertEquals(SYSTEM_PROPERTY_VALUE, interpolator.apply("sys:" + SYSTEM_PROPERTY_NAME));
    }

    /** The "env:" prefix should resolve an existing environment variable (PATH). */
    private void assertEnvironmentLookup(final StringLookup interpolator) {
        assertNotNull(interpolator.apply("env:PATH"));
    }

    /** The "date:" prefix should format the current date using the supplied pattern. */
    private void assertDateLookup(final StringLookup interpolator) {
        final String lookedUpDate = interpolator.apply("date:yyyy-MM-dd");
        assertNotNull(lookedUpDate, "No Date");

        final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(lookedUpDate, today);
    }

    /** Each "java:" sub-key should resolve to some non-empty runtime description. */
    private void assertJavaRuntimeLookups(final StringLookup interpolator) {
        assertNonEmptyLookup(interpolator, "java:version");
        assertNonEmptyLookup(interpolator, "java:runtime");
        assertNonEmptyLookup(interpolator, "java:vm");
        assertNonEmptyLookup(interpolator, "java:os");
        assertNonEmptyLookup(interpolator, "java:locale");
        assertNonEmptyLookup(interpolator, "java:hardware");
    }

    /** Asserts that looking up {@code key} yields a value that is neither null nor empty. */
    private void assertNonEmptyLookup(final StringLookup interpolator, final String key) {
        final String value = interpolator.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }
}
