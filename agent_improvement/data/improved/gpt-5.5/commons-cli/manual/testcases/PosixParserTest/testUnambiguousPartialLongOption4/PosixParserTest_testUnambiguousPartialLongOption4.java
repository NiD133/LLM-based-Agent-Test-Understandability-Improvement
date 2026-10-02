package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Documents that {@link PosixParser} intentionally does not support the
 * inherited unambiguous partial long option scenario.
 */
public class PosixParserTest_testUnambiguousPartialLongOption4 extends AbstractParserTestCase {

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
    void testUnambiguousPartialLongOption4() throws Exception {
    }
}
