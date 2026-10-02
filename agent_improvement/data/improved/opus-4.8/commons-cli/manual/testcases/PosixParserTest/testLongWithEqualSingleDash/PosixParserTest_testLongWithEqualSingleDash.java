package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs the shared parser test suite ({@link AbstractParserTestCase}) against {@link PosixParser}.
 *
 * <p>This subclass exists only to plug a {@link PosixParser} instance into the inherited tests and
 * to opt out of the single test case that {@code PosixParser} does not support.</p>
 */
public class PosixParserTest_testLongWithEqualSingleDash extends AbstractParserTestCase {

    /**
     * Prepares the shared fixture and supplies a {@link PosixParser} as the parser under test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Overrides and disables the inherited test case.
     *
     * <p>The "long option with a single dash and an equals sign" syntax (for example {@code -foo=bar})
     * is not supported by {@link PosixParser}, so this scenario is intentionally skipped rather than run.</p>
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithEqualSingleDash() throws Exception {
        // Intentionally empty: the disabled annotation prevents this test from executing.
    }
}
