package org.apache.commons.codec.binary;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#StringUtils()}.
 */
public class StringUtilsTest_testConstructor {

    /**
     * Verifies that the public (deprecated) no-argument constructor can be invoked.
     *
     * <p>
     * {@code StringUtils} only exposes static helper methods, so an instance is never
     * actually needed. The constructor is kept public rather than private because there
     * is no real benefit to restricting instantiation; this test simply documents that it
     * remains callable.
     * </p>
     */
    @Test
    void testConstructor() {
        new StringUtils();
    }
}
