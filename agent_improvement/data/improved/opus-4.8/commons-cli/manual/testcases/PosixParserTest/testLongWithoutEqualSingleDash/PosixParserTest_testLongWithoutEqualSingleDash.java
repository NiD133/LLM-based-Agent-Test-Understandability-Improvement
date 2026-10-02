package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PosixParser} behavior for the inherited
 * {@code testLongWithoutEqualSingleDash} scenario.
 *
 * <p>This test case wires the shared {@link AbstractParserTestCase} fixtures to a
 * {@link PosixParser} instance. The single-dash long-option syntax exercised by the
 * inherited test (for example {@code -foo value} instead of {@code --foo value}) is
 * not supported by {@link PosixParser}, so the inherited test is intentionally
 * disabled and overridden with an empty body.</p>
 */
public class PosixParserTest_testLongWithoutEqualSingleDash extends AbstractParserTestCase {

    /**
     * Initializes the shared parser fixture with a {@link PosixParser} before each test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: {@link PosixParser} does not support the single-dash long-option syntax,
     * so this inherited scenario is intentionally skipped.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithoutEqualSingleDash() throws Exception {
        // Intentionally empty: the inherited test is disabled for PosixParser.
    }
}
