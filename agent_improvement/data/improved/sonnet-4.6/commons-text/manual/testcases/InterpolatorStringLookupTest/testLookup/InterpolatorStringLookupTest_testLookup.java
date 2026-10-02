package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookup {

    private static final String TESTKEY = "TestKey";
    private static final String TESTKEY2 = "TestKey2";
    private static final String TESTVAL = "TestValue";

    /** Prefix that routes lookup to the default (map-backed) lookup. */
    private static final String CTX_PREFIX = "ctx:";

    /** Prefix that routes lookup to Java system properties. */
    private static final String SYS_PREFIX = "sys:";

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

    @Test
    void testLookup() {
        // Build an interpolator lookup backed by an in-memory map.
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);
        final StringLookup lookup =
                new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));

        // A bare key (no prefix) is resolved by the default map lookup.
        assertEquals(TESTVAL, lookup.apply(TESTKEY));

        // The "ctx:" prefix also delegates to the default map lookup.
        assertEquals(TESTVAL, lookup.apply(CTX_PREFIX + TESTKEY));

        // The "sys:" prefix delegates to Java system properties.
        assertEquals(TESTVAL, lookup.apply(SYS_PREFIX + TESTKEY));

        // A key that does not exist in any registered lookup returns null.
        assertNull(lookup.apply("BadKey"));

        // "ctx:" lookup is idempotent — repeated calls return the same result.
        assertEquals(TESTVAL, lookup.apply(CTX_PREFIX + TESTKEY));
    }
}
