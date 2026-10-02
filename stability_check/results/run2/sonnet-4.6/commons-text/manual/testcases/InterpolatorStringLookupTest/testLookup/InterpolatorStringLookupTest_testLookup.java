package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InterpolatorStringLookup} correctly dispatches lookups
 * based on key prefixes (e.g. "ctx:", "sys:") and returns null for unknown keys.
 */
public class InterpolatorStringLookupTest_testLookup {

    /** System-property key set during test lifecycle and used as the lookup key. */
    private static final String TESTKEY = "TestKey";

    /** A second system-property key registered alongside TESTKEY. */
    private static final String TESTKEY2 = "TestKey2";

    /** The value stored under both test keys. */
    private static final String TESTVAL = "TestValue";

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(TESTKEY, TESTVAL);
        System.setProperty(TESTKEY2, TESTVAL);
    }

    @AfterAll
    public static void afterAll() {
        System.clearProperty(TESTKEY);
        System.clearProperty(TESTKEY2);
    }

    /**
     * Verifies the three key-resolution paths supported by {@link InterpolatorStringLookup}:
     * <ol>
     *   <li>A bare key (no prefix) is resolved via the default lookup (the backing map).</li>
     *   <li>The {@code ctx:} prefix explicitly targets the default (context/map) lookup.</li>
     *   <li>The {@code sys:} prefix routes the key to system properties.</li>
     * </ol>
     * Also verifies that a key that matches none of these sources returns {@code null}.
     */
    @Test
    void testLookup() {
        // Prepare a map-backed default lookup containing TESTKEY -> TESTVAL
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);
        final StringLookup lookup = new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));

        // --- 1. Bare key: resolved via the default (map) lookup ---
        String value = lookup.apply(TESTKEY);
        assertEquals(TESTVAL, value);

        // --- 2. "ctx:" prefix: explicitly routes to the default (map) lookup ---
        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);

        // --- 3. "sys:" prefix: routes to system properties (set in @BeforeAll) ---
        value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value);

        // --- 4. Unknown key with no matching prefix returns null ---
        value = lookup.apply("BadKey");
        assertNull(value);

        // --- 5. "ctx:" prefix lookup is deterministic across repeated calls ---
        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);
    }
}
