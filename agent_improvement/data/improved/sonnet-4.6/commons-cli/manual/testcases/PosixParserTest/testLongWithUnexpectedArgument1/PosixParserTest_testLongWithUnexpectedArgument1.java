package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests the PosixParser's behaviour for the {@code testLongWithUnexpectedArgument1} scenario,
 * which verifies that passing an unexpected value via {@code --option=value} syntax to a
 * no-argument long option raises an {@link UnrecognizedOptionException}.
 *
 * <p>PosixParser does not support this detection: instead of throwing an exception it silently
 * treats the value part as an unrelated token.  The test is therefore disabled for this parser.</p>
 */
public class PosixParserTest_testLongWithUnexpectedArgument1 extends AbstractParserTestCase {

    /**
     * Initialises the shared {@link AbstractParserTestCase} fixture and installs a
     * {@link PosixParser} as the parser under test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Skipped because {@link PosixParser} does not throw {@link UnrecognizedOptionException}
     * when an argument is unexpectedly supplied to a no-argument long option via {@code =} syntax
     * (e.g. {@code --foo=bar} where {@code --foo} accepts no argument).
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithUnexpectedArgument1() throws Exception {
    }
}
