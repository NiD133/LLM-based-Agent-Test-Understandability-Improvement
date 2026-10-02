package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests that the deprecated {@link NullInputStream#INSTANCE} singleton is
 * available and non-null.
 */
public class NullInputStreamTest_testDeprecatedSingleton {

    /**
     * The deprecated shared singleton should still be exposed as a usable,
     * non-null instance for backwards compatibility.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testDeprecatedSingleton() {
        assertNotNull(NullInputStream.INSTANCE);
    }
}
