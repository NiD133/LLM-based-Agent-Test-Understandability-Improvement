package org.apache.commons.text.lookup;

import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookupKeys {

    private static final String TESTKEY = "TestKey";
    private static final String TESTKEY2 = "TestKey2";
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

    @Test
    void testLookupKeys() {
        final InterpolatorStringLookup lookup = new InterpolatorStringLookup((Map<String, Object>) null);
        final Map<String, StringLookup> stringLookupMap = lookup.getStringLookupMap();

        StringLookupFactoryTest.assertDefaultKeys(stringLookupMap);
    }
}
