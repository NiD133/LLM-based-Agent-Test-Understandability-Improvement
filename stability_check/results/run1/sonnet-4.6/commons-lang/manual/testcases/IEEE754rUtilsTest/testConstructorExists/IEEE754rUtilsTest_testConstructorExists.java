package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    /**
     * Verifies that the deprecated public constructor of IEEE754rUtils can still be
     * instantiated, ensuring backward compatibility until the constructor is made
     * private in a future major release.
     */
    @Test
    void testConstructorExists() {
        // IEEE754rUtils has a deprecated-but-public constructor; confirm it is accessible
        IEEE754rUtils instance = new IEEE754rUtils();
        assertNotNull(instance, "IEEE754rUtils constructor should produce a non-null instance");
    }
}
