package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testDoubleDash2 extends AbstractParserTestCase {

    private static final String POSIX_PARSER_UNSUPPORTED_DOUBLE_DASH_BEHAVIOR =
            "not supported by the PosixParser";

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    @Override
    @Test
    @Disabled(POSIX_PARSER_UNSUPPORTED_DOUBLE_DASH_BEHAVIOR)
    void testDoubleDash2() throws Exception {
        // Intentionally empty: the inherited double-dash scenario is disabled for PosixParser.
    }
}
