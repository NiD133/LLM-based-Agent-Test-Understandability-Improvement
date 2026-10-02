package org.apache.commons.lang3;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testConstructable extends AbstractLangTest {

    @Test
    void testConstructable() {
        // The public constructor is retained for JavaBean-style tools.
        new EnumUtils();
    }
}
