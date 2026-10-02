package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Documents that {@link PosixParser} intentionally does not run the inherited
 * ambiguous single-dash long-option scenario.
 */
public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash2 extends AbstractParserTestCase {

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
        // The inherited assertion is deliberately disabled for this parser.
    }
}
