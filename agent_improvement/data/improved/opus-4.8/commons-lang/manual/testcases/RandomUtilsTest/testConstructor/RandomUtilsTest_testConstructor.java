package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the deprecated public no-arg {@link RandomUtils} constructor.
 * <p>
 * The constructor exists only so that bean-based tools can instantiate the class; this
 * test simply confirms that calling it yields a usable, non-null instance.
 * </p>
 */
public class RandomUtilsTest_testConstructor extends AbstractLangTest {

    @Test
    void constructorReturnsNonNullInstance() {
        assertNotNull(new RandomUtils());
    }
}
