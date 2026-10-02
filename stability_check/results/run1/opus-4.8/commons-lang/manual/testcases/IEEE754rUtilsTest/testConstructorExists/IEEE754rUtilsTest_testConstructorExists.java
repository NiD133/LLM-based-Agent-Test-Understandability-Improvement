package org.apache.commons.lang3.math;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for the public (deprecated) no-argument constructor of {@link IEEE754rUtils}.
 */
public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    /**
     * Verifies that the deprecated public no-argument constructor is available
     * and can be invoked without throwing an exception.
     */
    @Test
    void testConstructorExists() {
        // Simply instantiating the utility class must succeed.
        new IEEE754rUtils();
    }
}
