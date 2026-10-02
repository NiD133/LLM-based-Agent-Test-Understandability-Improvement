package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link InterpolatorStringLookup} resolves keys, either directly through its
 * default (map-backed) lookup or through a prefixed lookup such as {@code sys:} and {@code ctx:}.
 */
public class InterpolatorStringLookupTest_testLookup {

    /** Key registered both as a system property and in the backing map. */
    private static final String TEST_KEY = "TestKey";

    /** Second key registered only as a system property (kept for parity with the original fixture). */
    private static final String SECOND_TEST_KEY = "TestKey2";

    /** Value associated with every registered key. */
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
        // The interpolator's default lookup is backed by this single-entry map.
        final Map<String, String> backingMap = new HashMap<>();
        backingMap.put(TEST_KEY, TEST_VALUE);
        final StringLookup lookup =
                new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(backingMap));

        // An unprefixed key falls through to the default (map) lookup.
        assertEquals(TEST_VALUE, lookup.apply(TEST_KEY));

        // The "ctx:" prefix also resolves against the map-backed default lookup.
        assertEquals(TEST_VALUE, lookup.apply("ctx:" + TEST_KEY));

        // The "sys:" prefix resolves against system properties (set in @BeforeAll).
        assertEquals(TEST_VALUE, lookup.apply("sys:" + TEST_KEY));

        // A key that is unknown to every lookup resolves to null.
        assertNull(lookup.apply("BadKey"));

        // Re-resolving the "ctx:" key still returns the same value.
        assertEquals(TEST_VALUE, lookup.apply("ctx:" + TEST_KEY));
    }
}
