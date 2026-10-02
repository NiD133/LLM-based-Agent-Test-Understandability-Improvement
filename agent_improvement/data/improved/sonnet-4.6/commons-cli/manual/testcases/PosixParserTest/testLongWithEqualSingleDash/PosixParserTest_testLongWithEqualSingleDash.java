package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PosixParser} that override the shared {@link AbstractParserTestCase} suite.
 *
 * <p>PosixParser is a deprecated, POSIX-style command-line parser. It differs from
 * {@link DefaultParser} in that a single-dash token (e.g. {@code -foo}) is "burst" into
 * individual short-option characters ({@code -f}, {@code -o}, {@code -o}) rather than
 * being treated as a long option. As a result, the {@code -longOpt=value} (long option
 * with equals sign using a single dash) syntax is not supported by this parser.</p>
 *
 * @deprecated PosixParser itself is deprecated since Commons CLI 1.3.
 */
public class PosixParserTest_testLongWithEqualSingleDash extends AbstractParserTestCase {

    /**
     * Instantiates a {@link PosixParser} as the parser under test before each test method.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Overrides the parent test and marks it disabled because {@link PosixParser} does not
     * support the {@code -longOpt=value} syntax. When PosixParser encounters a single-dash
     * token longer than two characters, it bursts the token into individual short options
     * instead of recognising it as a long option with an equals-delimited value.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithEqualSingleDash() throws Exception {
    }
}
