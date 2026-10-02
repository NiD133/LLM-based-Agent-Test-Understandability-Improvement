package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class NullInputStreamTest_testDeprecatedSingleton {

    @SuppressWarnings("deprecation")
    @Test
    void testDeprecatedSingleton() throws Exception {
        assertNotNull(NullInputStream.INSTANCE);
    }
}
