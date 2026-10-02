package org.apache.commons.lang3.math;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    /**
     * Verifies that the deprecated public no-arg constructor of IEEE754rUtils
     * can be instantiated without throwing an exception.
     * The constructor is retained for binary compatibility and is marked
     * @Deprecated pending removal in a future major version.
     */
    @Test
    void testConstructorExists() {
        new IEEE754rUtils();
    }
}
