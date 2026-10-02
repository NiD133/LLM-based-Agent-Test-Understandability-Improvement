package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testLongWithEqualSingleDash extends AbstractParserTestCase {

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
    void testLongWithEqualSingleDash() throws Exception {
        // Intentionally empty: this inherited parser scenario is unsupported by PosixParser.
    }
}
