package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies {@link PosixParser} against the shared parser test suite defined in
 * {@link AbstractParserTestCase}.
 *
 * <p>This class focuses on the {@code testDoubleDash2} scenario, which is
 * intentionally disabled: the behaviour it exercises is not supported by
 * {@link PosixParser}.</p>
 */
public class PosixParserTest_testDoubleDash2 extends AbstractParserTestCase {

    /**
     * Installs a {@link PosixParser} as the parser under test before each case.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: the "double dash" behaviour covered by this case is not
     * supported by {@link PosixParser}, so the inherited test is overridden
     * with an empty body and skipped.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testDoubleDash2() throws Exception {
        // Intentionally empty: scenario unsupported by PosixParser.
    }
}
