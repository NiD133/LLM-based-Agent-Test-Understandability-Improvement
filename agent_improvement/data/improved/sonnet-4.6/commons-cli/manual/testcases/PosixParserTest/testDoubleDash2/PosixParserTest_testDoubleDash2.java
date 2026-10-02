package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link PosixParser} that override base-class cases unsupported by this parser.
 *
 * <p>The base class {@code AbstractParserTestCase#testDoubleDash2} verifies that when "--"
 * appears after an option that requires an argument (e.g. {@code -n -- -m}), the parser
 * throws {@link MissingArgumentException} because "--" ends option processing before {@code -n}
 * receives its value. {@link PosixParser} does not implement this behaviour, so the test is
 * disabled here.
 */
public class PosixParserTest_testDoubleDash2 extends AbstractParserTestCase {

    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled because {@link PosixParser} does not treat "--" as a terminator that triggers
     * {@link MissingArgumentException} when a preceding option still awaits its argument.
     * The base-class assertion ({@code assertThrows(MissingArgumentException.class, ...)})
     * would fail against this parser.
     */
    @Override
    @Test
    @Disabled("PosixParser does not throw MissingArgumentException when '--' follows an option"
            + " that still expects an argument; this behaviour is specific to DefaultParser.")
    @DisplayName("Double-dash after option requiring argument should throw MissingArgumentException"
            + " (not supported by PosixParser)")
    void testDoubleDash2() throws Exception {
    }
}
