package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests how {@link InterpolatorStringLookup} handles a {@code null} key.
 */
public class InterpolatorStringLookupTest_testNull {

    /** System property keys seeded for the lookup tests. */
    private static final String TESTKEY = "TestKey";
    private static final String TESTKEY2 = "TestKey2";

    /** Value stored under both system property keys. */
    private static final String TESTVAL = "TestValue";

    @BeforeAll
    public static void setUpSystemProperties() {
        System.setProperty(TESTKEY, TESTVAL);
        System.setProperty(TESTKEY2, TESTVAL);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(TESTKEY);
        System.clearProperty(TESTKEY2);
    }

    /**
     * Looking up a {@code null} key must resolve to {@code null} rather than throwing.
     */
    @Test
    void testNull() {
        assertNull(InterpolatorStringLookup.INSTANCE.apply(null));
    }
}
