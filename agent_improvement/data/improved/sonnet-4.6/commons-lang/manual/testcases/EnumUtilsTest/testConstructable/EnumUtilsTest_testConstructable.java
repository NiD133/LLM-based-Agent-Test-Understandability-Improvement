package org.apache.commons.lang3;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testConstructable extends AbstractLangTest {

    /**
     * EnumUtils keeps a public no-arg constructor so that JavaBean-based tools
     * (e.g. dependency-injection containers) can instantiate it as a bean.
     * This test confirms the constructor remains accessible and does not throw.
     */
    @Test
    void testConstructable() {
        new EnumUtils();
    }
}
