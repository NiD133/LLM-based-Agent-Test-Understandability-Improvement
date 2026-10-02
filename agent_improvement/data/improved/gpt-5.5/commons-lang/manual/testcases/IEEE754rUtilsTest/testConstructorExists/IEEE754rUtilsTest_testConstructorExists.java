package org.apache.commons.lang3.math;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    @Test
    @SuppressWarnings("deprecation")
    void testConstructorExists() {
        // The constructor is deprecated but remains public for binary compatibility.
        new IEEE754rUtils();
    }
}
