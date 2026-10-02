package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    private static final String CONTEXT_LOOKUP_KEY = "ctx:" + TESTKEY;
    private static final String SYSTEM_LOOKUP_KEY = "sys:" + TESTKEY;
    private static final String UNKNOWN_KEY = "BadKey";

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

    private static StringLookup createLookupWithTestValue() {
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);
        return new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));
    }

    private static void assertLookupValue(final StringLookup lookup, final String key, final String expectedValue) {
        final String value = lookup.apply(key);
        assertEquals(expectedValue, value);
    }

    private static void assertLookupResolves(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
    }

    private static void assertLookupIsUnresolved(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNull(value);
    }

    @Test
    void testLookup() {
        final StringLookup lookup = createLookupWithTestValue();

        assertLookupValue(lookup, TESTKEY, TESTVAL);
        assertLookupValue(lookup, CONTEXT_LOOKUP_KEY, TESTVAL);
        assertLookupValue(lookup, SYSTEM_LOOKUP_KEY, TESTVAL);
        assertLookupIsUnresolved(lookup, UNKNOWN_KEY);
        assertLookupValue(lookup, CONTEXT_LOOKUP_KEY, TESTVAL);
    }
}
