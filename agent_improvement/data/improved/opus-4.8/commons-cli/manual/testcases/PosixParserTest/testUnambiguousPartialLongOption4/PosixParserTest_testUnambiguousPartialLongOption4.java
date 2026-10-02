package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs the {@link AbstractParserTestCase} suite against the {@link PosixParser}.
 *
 * <p>This subclass exists only to bind the shared parser test cases to a
 * {@code PosixParser} instance. The single test it carries,
 * {@code testUnambiguousPartialLongOption4}, is intentionally disabled because
 * unambiguous partial long-option matching is a feature the PosixParser does
 * not implement.</p>
 */
public class PosixParserTest_testUnambiguousPartialLongOption4 extends AbstractParserTestCase {

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Overrides the inherited test so it is skipped for the PosixParser, which
     * does not support partial long-option resolution.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testUnambiguousPartialLongOption4() throws Exception {
        // Intentionally empty: disabled because the feature is unsupported here.
    }
}
