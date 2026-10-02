package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs {@link AbstractParserTestCase} against the {@link PosixParser}.
 *
 * <p>The inherited {@code testAmbiguousLongWithoutEqualSingleDash2} scenario exercises the
 * abbreviation of a long option supplied with a single dash and without an {@code =} sign
 * (for example {@code -foo} instead of {@code --foo=value}). The {@link PosixParser} does not
 * support this form, so the test is overridden with an empty, disabled body to opt out of the
 * shared scenario for this parser implementation.</p>
 */
public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash2 extends AbstractParserTestCase {

    /**
     * Configures the shared test fixture to use the {@link PosixParser}.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: the single-dash, no-{@code =} long-option abbreviation is not supported by the
     * {@link PosixParser}, so the inherited scenario is intentionally skipped.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
        // Intentionally empty: behaviour is unsupported by the PosixParser.
    }
}
