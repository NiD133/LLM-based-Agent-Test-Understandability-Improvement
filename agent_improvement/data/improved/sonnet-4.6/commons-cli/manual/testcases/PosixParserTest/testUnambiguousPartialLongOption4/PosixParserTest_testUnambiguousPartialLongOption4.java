package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link PosixParser} behaviour for the "unambiguous partial long option with single-dash
 * prefix and inline value" case (e.g. {@code -ver=1} should resolve to {@code --verbose} with
 * value {@code "1"}).
 *
 * <p>This specific scenario is not supported by {@link PosixParser}, so the inherited test
 * is disabled here. The parent class ({@link AbstractParserTestCase}) defines the full test
 * body; this subclass only skips it.
 */
public class PosixParserTest_testUnambiguousPartialLongOption4 extends AbstractParserTestCase {

    /**
     * Instantiates the system-under-test as a (deprecated) {@link PosixParser} before each test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Overrides the parent test to mark it disabled.
     *
     * <p>The parent test verifies that {@code -ver=1} is unambiguously matched against
     * the {@code --verbose} long option and that the inline value {@code "1"} is extracted
     * correctly. {@link PosixParser} does not perform partial long-option matching when a
     * single-dash prefix is combined with an {@code =value} suffix, so this case cannot
     * be supported and the test must be skipped.
     */
    @Override
    @Test
    @Disabled("PosixParser does not support partial long option matching with a single-dash "
            + "prefix and an inline value (e.g. -ver=1 → --verbose with value 1)")
    void testUnambiguousPartialLongOption4() throws Exception {
    }
}
