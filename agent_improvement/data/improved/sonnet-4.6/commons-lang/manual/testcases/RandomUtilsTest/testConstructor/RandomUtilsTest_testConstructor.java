package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated public no-arg constructor of {@link RandomUtils}, which exists
 * solely to support JavaBean-based tools that require a default constructor.
 */
public class RandomUtilsTest_testConstructor extends AbstractLangTest {

    @Test
    void testConstructor() {
        // The no-arg constructor is deprecated but public; verify it produces a valid instance.
        assertNotNull(new RandomUtils());
    }
}
