package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testNegativeOption extends AbstractParserTestCase {

    private static final String POSIX_PARSER_DOES_NOT_SUPPORT_NEGATIVE_OPTIONS =
            "not supported by the PosixParser (CLI-184)";

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    @Override
    @Test
    @Disabled(POSIX_PARSER_DOES_NOT_SUPPORT_NEGATIVE_OPTIONS)
    void testNegativeOption() throws Exception {
        // Intentionally empty: the inherited negative-option contract is disabled for PosixParser.
    }
}
