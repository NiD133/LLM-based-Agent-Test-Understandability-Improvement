package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PosixParser} behaviour for the "short option with equals sign" scenario
 * (for example {@code -f=value}).
 *
 * <p>This test case inherits the shared parser test suite from {@link AbstractParserTestCase}
 * but supplies a {@link PosixParser} as the parser under test. The
 * {@code testShortWithEqual} scenario is intentionally disabled here because the
 * {@link PosixParser} does not support attaching a value to a short option using an
 * equals sign, so the inherited assertions do not apply to this parser.</p>
 */
public class PosixParserTest_testShortWithEqual extends AbstractParserTestCase {

    /**
     * Configures the shared test fixture to exercise the {@link PosixParser}.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: the {@code "-f=value"} (short option with equals) syntax is not
     * supported by {@link PosixParser}, so the inherited test does not run for this parser.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testShortWithEqual() throws Exception {
        // Intentionally left empty; the inherited scenario is disabled for the PosixParser.
    }
}
