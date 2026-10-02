package org.apache.commons.codec.binary;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testConstructor {

    /**
     * Verifies that {@link StringUtils} can be instantiated via its public constructor.
     * The constructor is intentionally left public (rather than made private) because
     * the utility class does not enforce non-instantiability at the language level.
     */
    @Test
    void testConstructor() {
        new StringUtils();
    }
}
