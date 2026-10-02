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

    @Test
    void testLookup() {
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);

        final StringLookup lookup = new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));

        assertEquals(TESTVAL, lookup.apply(TESTKEY));
        assertEquals(TESTVAL, lookup.apply("ctx:" + TESTKEY));
        assertEquals(TESTVAL, lookup.apply("sys:" + TESTKEY));
        assertNull(lookup.apply("BadKey"));
        assertEquals(TESTVAL, lookup.apply("ctx:" + TESTKEY));
    }
}
