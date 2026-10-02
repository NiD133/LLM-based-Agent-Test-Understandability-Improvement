package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link PosixParser} behavior for the negative-option scenario.
 *
 * <p>{@link PosixParser} is a deprecated CLI parser that interprets arguments
 * in POSIX style (e.g. {@code -abc} is burst into {@code -a -b -c}).
 * Unlike {@link DefaultParser}, it does not support negative numeric options
 * (e.g. {@code -1}) — see CLI-184. The inherited {@link #testNegativeOption()}
 * test is therefore disabled for this parser.</p>
 */
public class PosixParserTest_testNegativeOption extends AbstractParserTestCase {

    /**
     * Instantiates the deprecated {@link PosixParser}.
     * The {@code @SuppressWarnings("deprecation")} is required because
     * {@link PosixParser} is marked {@link Deprecated} since commons-cli 1.3.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled because {@link PosixParser} does not support negative numeric
     * options such as {@code -1} (CLI-184). The body is intentionally empty;
     * the {@code @Disabled} annotation prevents a false test failure.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser (CLI-184)")
    void testNegativeOption() throws Exception {
    }
}
