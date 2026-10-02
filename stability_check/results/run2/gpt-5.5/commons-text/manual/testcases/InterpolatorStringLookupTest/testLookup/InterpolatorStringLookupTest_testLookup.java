package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InterpolatorStringLookupTest_testLookup {

    private static final String MAPPED_KEY = "TestKey";
    private static final String SYSTEM_ONLY_KEY = "TestKey2";
    private static final String TEST_VALUE = "TestValue";

    @AfterAll
    public static void afterAll() {
        System.clearProperty(MAPPED_KEY);
        System.clearProperty(SYSTEM_ONLY_KEY);
    }

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(MAPPED_KEY, TEST_VALUE);
        System.setProperty(SYSTEM_ONLY_KEY, TEST_VALUE);
    }

    @Test
    void testLookup() {
        final Map<String, String> defaultValues = new HashMap<>();
        defaultValues.put(MAPPED_KEY, TEST_VALUE);

        final StringLookup lookup = new InterpolatorStringLookup(
            StringLookupFactory.INSTANCE.mapStringLookup(defaultValues));

        assertResolvesToTestValue(lookup, MAPPED_KEY);
        assertResolvesToTestValue(lookup, "ctx:" + MAPPED_KEY);
        assertResolvesToTestValue(lookup, "sys:" + MAPPED_KEY);
        assertNull(lookup.apply("BadKey"));
        assertResolvesToTestValue(lookup, "ctx:" + MAPPED_KEY);
    }

    private void assertResolvesToTestValue(final StringLookup lookup, final String key) {
        assertEquals(TEST_VALUE, lookup.apply(key));
    }
}
