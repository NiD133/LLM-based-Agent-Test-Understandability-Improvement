package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PosixParser} focusing on the short-option-with-equals syntax (e.g. {@code -f=bar}).
 *
 * <p>PosixParser does not support the {@code -f=bar} form for short options; the equals sign is
 * treated as part of the option argument rather than a separator. The inherited
 * {@code testShortWithEqual} test is therefore disabled for this parser.
 */
public class PosixParserTest_testShortWithEqual extends AbstractParserTestCase {

    /**
     * Initialises the parser under test with a {@link PosixParser} instance before each test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * PosixParser does not recognise the equals sign as an argument separator for short options,
     * so this test is disabled. Use {@link DefaultParser} for short-option-with-equals support.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    @DisplayName("Short option with equals separator (-f=bar) is not supported by PosixParser")
    void testShortWithEqual() throws Exception {
    }
}
