package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Runs the {@link AbstractParserTestCase} suite against the {@link PosixParser}.
 *
 * <p>This subclass plugs a {@link PosixParser} into the shared parser test fixture and then
 * overrides {@code testLongWithUnexpectedArgument1} to disable it: the scenario it checks
 * (a long option that is given an unexpected attached argument) is not supported by the
 * {@code PosixParser}, so the inherited assertions do not apply here.</p>
 */
public class PosixParserTest_testLongWithUnexpectedArgument1 extends AbstractParserTestCase {

    /**
     * Builds the shared fixture and then swaps in the {@link PosixParser} as the parser under test.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled: the {@link PosixParser} does not support the "long option with unexpected argument"
     * case exercised by the inherited test, so the body is intentionally empty.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithUnexpectedArgument1() throws Exception {
    }
}
