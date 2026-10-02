package org.apache.commons.lang3.math;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests the public, no-argument constructor of {@link IEEE754rUtils}.
 *
 * <p>Although {@code IEEE754rUtils} only exposes static helper methods, it still
 * declares a public (deprecated) no-argument constructor for backwards
 * compatibility. This test simply verifies that the constructor is present and
 * can be invoked without throwing.</p>
 */
public class IEEE754rUtilsTest_testConstructorExists extends AbstractLangTest {

    @Test
    void constructorCanBeInvoked() {
        // The instance is intentionally unused; invoking the constructor
        // without an exception is the behaviour under test.
        new IEEE754rUtils();
    }
}
