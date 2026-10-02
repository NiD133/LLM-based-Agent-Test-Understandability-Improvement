package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs the shared parser test suite ({@link AbstractParserTestCase}) against the
 * {@link PosixParser} implementation.
 *
 * <p>The only behaviour customised here is the parser instance used by the inherited
 * tests; every other test case is provided by the superclass.</p>
 */
public class PosixParserTest_testNegativeOption extends AbstractParserTestCase {

    /**
     * Wires the shared test fixture up with a {@link PosixParser} so that all inherited
     * test cases exercise the POSIX-style parsing behaviour.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled for {@link PosixParser}: parsing of negative-number options (for example
     * {@code -42}) is not supported by this parser, as documented in CLI-184. The method
     * body is intentionally empty because the test is skipped rather than executed.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser (CLI-184)")
    void testNegativeOption() throws Exception {
        // Intentionally empty: see @Disabled reason above.
    }
}
