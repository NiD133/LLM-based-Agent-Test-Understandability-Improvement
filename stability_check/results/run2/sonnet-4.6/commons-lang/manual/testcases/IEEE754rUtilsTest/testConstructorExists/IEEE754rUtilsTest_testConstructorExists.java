package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    /**
     * Verifies that the deprecated public no-arg constructor of IEEE754rUtils
     * is accessible and produces a non-null instance.
     */
    @Test
    void testConstructorExists() {
        IEEE754rUtils instance = new IEEE754rUtils();
        assertNotNull(instance, "IEEE754rUtils public constructor should produce a non-null instance");
    }
}
