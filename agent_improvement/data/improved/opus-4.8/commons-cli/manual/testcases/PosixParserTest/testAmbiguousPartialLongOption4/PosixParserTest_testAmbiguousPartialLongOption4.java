package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs the shared parser test suite from {@link AbstractParserTestCase} against {@link PosixParser}.
 *
 * <p>This class focuses on the {@code testAmbiguousPartialLongOption4} scenario, which exercises how
 * a parser resolves an abbreviated long option that matches more than one defined option. The
 * {@link PosixParser} does not support this partial long option matching, so the inherited test is
 * overridden and disabled here.</p>
 */
public class PosixParserTest_testAmbiguousPartialLongOption4 extends AbstractParserTestCase {

    /**
     * Initializes the shared parser fixture and selects {@link PosixParser} as the parser under test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: {@link PosixParser} does not support ambiguous partial long option resolution,
     * so the inherited behavior is intentionally not exercised for this parser.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousPartialLongOption4() throws Exception {
        // No body: the inherited test is overridden solely to disable it for PosixParser.
    }
}
