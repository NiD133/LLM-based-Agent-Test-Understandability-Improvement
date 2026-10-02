package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testShortWithEqual extends AbstractParserTestCase {

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
    void testShortWithEqual() throws Exception {
        // Intentionally empty: this inherited parser contract is disabled for PosixParser.
    }
}
