package org.apache.commons.text.lookup;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link InterpolatorStringLookup} created without any custom lookups
 * is populated with the factory's default set of string lookups.
 */
public class InterpolatorStringLookupTest_testLookupKeys {

    /** System property key seeded before the tests run. */
    private static final String TEST_KEY = "TestKey";

    /** Second system property key seeded before the tests run. */
    private static final String TEST_KEY_2 = "TestKey2";

    /** Value assigned to the seeded system properties. */
    private static final String TEST_VALUE = "TestValue";

    @BeforeAll
    public static void setUpSystemProperties() {
        System.setProperty(TEST_KEY, TEST_VALUE);
        System.setProperty(TEST_KEY_2, TEST_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(TEST_KEY);
        System.clearProperty(TEST_KEY_2);
    }

    @Test
    void testLookupKeys() {
        // Build an interpolator with no caller-supplied lookup map, so it falls back to the defaults.
        final InterpolatorStringLookup lookup = new InterpolatorStringLookup((Map<String, Object>) null);

        final Map<String, StringLookup> stringLookupMap = lookup.getStringLookupMap();

        // The map should contain exactly the default lookup keys provided by the factory.
        StringLookupFactoryTest.assertDefaultKeys(stringLookupMap);
    }
}
