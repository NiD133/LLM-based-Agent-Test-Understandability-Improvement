package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * PosixParser-specific override for the ambiguous long option (single-dash, no equals) test.
 *
 * <p>The inherited test ({@code testAmbiguousLongWithoutEqualSingleDash2}) verifies that a parser
 * can handle an argument like {@code -foobar} by treating it as the long option {@code --foo} with
 * value {@code bar}. PosixParser does not implement this disambiguation; it burst-parses
 * single-dash tokens character by character and therefore cannot resolve such ambiguous long
 * options. This override explicitly marks the test as not applicable for PosixParser.</p>
 */
public class PosixParserTest_testAmbiguousLongWithoutEqualSingleDash2 extends AbstractParserTestCase {

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled because PosixParser does not support disambiguation of long options expressed
     * with a single dash and no equals sign (e.g. {@code -foobar} as {@code --foo=bar}).
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousLongWithoutEqualSingleDash2() throws Exception {
    }
}
