package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class RandomUtilsTest_testConstructor extends AbstractLangTest {

    @Test
    void testConstructor() {
        assertNotNull(new RandomUtils());
    }
}
