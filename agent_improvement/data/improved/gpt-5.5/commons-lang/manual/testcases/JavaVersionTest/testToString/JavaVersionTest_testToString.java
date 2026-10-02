package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JavaVersionTest_testToString extends AbstractLangTest {

    @Test
    void toStringReturnsDeclaredVersionLabel() {
        assertEquals("1.2", JavaVersion.JAVA_1_2.toString());
    }
}
