package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link InterpolatorStringLookup} resolves keys, focusing on the way it
 * dispatches between a prefixed lookup (e.g. {@code "ctx:"} or {@code "sys:"}) and
 * its default lookup, which here is backed by an in-memory map.
 */
public class InterpolatorStringLookupTest_testLookup {

    /** A key that is present both in the system properties and in the in-memory map. */
    private static final String KNOWN_KEY = "TestKey";

    /** A second key registered only as a system property (kept to mirror the original fixture). */
    private static final String SYSTEM_ONLY_KEY = "TestKey2";

    /** The value associated with every known key. */
    private static final String KNOWN_VALUE = "TestValue";

    @BeforeAll
    public static void registerSystemProperties() {
        System.setProperty(KNOWN_KEY, KNOWN_VALUE);
        System.setProperty(SYSTEM_ONLY_KEY, KNOWN_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(KNOWN_KEY);
        System.clearProperty(SYSTEM_ONLY_KEY);
    }

    @Test
    void testLookup() {
        // The default lookup is a map containing a single known key/value pair.
        final Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put(KNOWN_KEY, KNOWN_VALUE);
        final StringLookup lookup =
                new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(defaultMap));

        // An unprefixed key falls through to the default (map) lookup.
        assertEquals(KNOWN_VALUE, lookup.apply(KNOWN_KEY));

        // The "ctx:" prefix resolves against the map-backed default lookup.
        assertEquals(KNOWN_VALUE, lookup.apply("ctx:" + KNOWN_KEY));

        // The "sys:" prefix resolves against the system properties.
        assertEquals(KNOWN_VALUE, lookup.apply("sys:" + KNOWN_KEY));

        // A key that exists in no lookup resolves to null.
        assertNull(lookup.apply("BadKey"));

        // Resolving an already-seen prefixed key again still returns the same value.
        assertEquals(KNOWN_VALUE, lookup.apply("ctx:" + KNOWN_KEY));
    }
}
