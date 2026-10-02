package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PosixParser} behavior for the "ambiguous long option without an equals sign,
 * written with a single dash" scenario inherited from {@link AbstractParserTestCase}.
 *
 * <p>This parser-specific subclass binds the shared test case to a {@link PosixParser} instance.
 * The scenario itself is intentionally disabled: the single-dash, no-equals form of an ambiguous
 * long option is not a feature the {@code PosixParser} supports, so the inherited test is overridden
 * with an empty, disabled body to exclude it from this parser's suite.</p>
 */
public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash extends AbstractParserTestCase {

    /**
     * Sets up the shared fixture and then points the parser under test at a {@link PosixParser}.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled for {@link PosixParser}: it does not support ambiguous long options expressed with a
     * single dash and no equals sign. The body is intentionally empty because the test never runs.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
        // Intentionally empty: this scenario is unsupported by PosixParser and is disabled above.
    }
}
