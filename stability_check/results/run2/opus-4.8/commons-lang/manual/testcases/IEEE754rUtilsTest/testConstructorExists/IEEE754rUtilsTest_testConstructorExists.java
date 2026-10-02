package org.apache.commons.lang3.math;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that the public (deprecated) no-argument constructor of
 * {@link IEEE754rUtils} is available and can be invoked without error.
 */
public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    @Test
    void testConstructorExists() {
        // The class only exposes static helpers, but retains a public constructor
        // for backwards compatibility; invoking it must not throw.
        new IEEE754rUtils();
    }
}
