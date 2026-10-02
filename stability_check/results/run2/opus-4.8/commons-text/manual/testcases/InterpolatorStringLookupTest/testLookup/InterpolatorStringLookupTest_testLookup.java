package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link InterpolatorStringLookup} resolves keys, both through its
 * default (map-backed) lookup and through prefixed lookups such as
 * {@code ctx:} and {@code sys:}.
 */
public class InterpolatorStringLookupTest_testLookup {

    /** Key registered both in the backing map and as a system property. */
    private static final String TEST_KEY = "TestKey";

    /** A second key registered only as a system property. */
    private static final String SECOND_TEST_KEY = "TestKey2";

    /** Value associated with the test keys. */
    private static final String TEST_VALUE = "TestValue";

    @BeforeAll
    public static void registerSystemProperties() {
        System.setProperty(TEST_KEY, TEST_VALUE);
        System.setProperty(SECOND_TEST_KEY, TEST_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(TEST_KEY);
        System.clearProperty(SECOND_TEST_KEY);
    }

    @Test
    void testLookup() {
        // Build an interpolator whose default lookup is backed by a single-entry map.
        final Map<String, String> backingMap = new HashMap<>();
        backingMap.put(TEST_KEY, TEST_VALUE);
        final StringLookup lookup =
            new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(backingMap));

        // An unprefixed key is resolved by the default (map-backed) lookup.
        assertEquals(TEST_VALUE, lookup.apply(TEST_KEY));

        // A "ctx:" prefix has no dedicated lookup, so it falls back to the default lookup.
        assertEquals(TEST_VALUE, lookup.apply("ctx:" + TEST_KEY));

        // A "sys:" prefix is resolved by the system-properties lookup.
        assertEquals(TEST_VALUE, lookup.apply("sys:" + TEST_KEY));

        // An unknown key cannot be resolved by any lookup.
        assertNull(lookup.apply("BadKey"));

        // Resolving the same prefixed key again is stable.
        assertEquals(TEST_VALUE, lookup.apply("ctx:" + TEST_KEY));
    }
}
