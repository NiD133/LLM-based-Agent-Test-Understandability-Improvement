package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OptionsTest_testSimple {

    private static final String OPTION_WITHOUT_ARGUMENT = "a";
    private static final String OPTION_WITH_ARGUMENT = "b";
    private static final String OPTION_WITHOUT_ARGUMENT_DESCRIPTION = "toggle -a";
    private static final String OPTION_WITH_ARGUMENT_DESCRIPTION = "toggle -b";

    @Test
    void testSimple() {
        final Options options = new Options();

        options.addOption(OPTION_WITHOUT_ARGUMENT, false, OPTION_WITHOUT_ARGUMENT_DESCRIPTION);
        options.addOption(OPTION_WITH_ARGUMENT, true, OPTION_WITH_ARGUMENT_DESCRIPTION);

        assertTrue(options.hasOption(OPTION_WITHOUT_ARGUMENT));
        assertTrue(options.hasOption(OPTION_WITH_ARGUMENT));
    }
}
