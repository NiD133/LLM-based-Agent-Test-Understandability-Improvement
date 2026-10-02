package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash extends AbstractParserTestCase {

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * PosixParser does not support the inherited ambiguous long-option scenario
     * for a single-dash token without an equals sign, so this override documents
     * the unsupported case without executing any assertions.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash() throws Exception {
    }
}
