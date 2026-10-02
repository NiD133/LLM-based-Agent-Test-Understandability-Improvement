package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testAmbiguousPartialLongOption4 extends AbstractParserTestCase {

    private static final String POSIX_PARSER_DOES_NOT_SUPPORT_THIS_CASE = "not supported by the PosixParser";

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    @Override
    @Test
    @Disabled(POSIX_PARSER_DOES_NOT_SUPPORT_THIS_CASE)
    void testAmbiguousPartialLongOption4() throws Exception {
        // Intentionally empty: the inherited parser contract includes this case,
        // but this PosixParser-specific override documents that it is unsupported.
    }
}
