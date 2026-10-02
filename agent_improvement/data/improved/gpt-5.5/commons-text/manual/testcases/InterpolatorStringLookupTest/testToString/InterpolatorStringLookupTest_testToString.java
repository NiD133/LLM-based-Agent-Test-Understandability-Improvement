package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testToString {

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
    void testToString() {
        final String description = new InterpolatorStringLookup().toString();

        assertFalse(description.isEmpty());
    }
}
