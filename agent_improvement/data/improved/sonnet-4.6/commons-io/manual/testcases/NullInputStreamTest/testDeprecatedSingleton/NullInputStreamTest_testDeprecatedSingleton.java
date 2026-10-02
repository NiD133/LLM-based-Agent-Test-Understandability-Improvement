package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated {@link NullInputStream#INSTANCE} singleton field.
 * The singleton exists for backward compatibility but is not recommended for
 * reuse without calling {@link NullInputStream#init()} to reset state.
 */
public class NullInputStreamTest_testDeprecatedSingleton {

    /**
     * Verifies that {@link NullInputStream#INSTANCE} has been initialized
     * and is accessible. Despite being deprecated, the field must remain
     * non-null as part of the public API contract.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testDeprecatedSingleton() throws Exception {
        assertNotNull(NullInputStream.INSTANCE,
                "NullInputStream.INSTANCE should be non-null: the deprecated singleton must be initialized on class load");
    }
}
