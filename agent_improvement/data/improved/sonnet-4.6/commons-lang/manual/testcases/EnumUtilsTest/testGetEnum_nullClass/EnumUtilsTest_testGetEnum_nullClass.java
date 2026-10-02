package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_nullClass extends AbstractLangTest {

    @Test
    void testGetEnum_nullClass() {
        // A null enumClass should cause getEnum to return null rather than throw.
        assertNull(EnumUtils.getEnum((Class<Traffic>) null, "PURPLE"));
    }
}
