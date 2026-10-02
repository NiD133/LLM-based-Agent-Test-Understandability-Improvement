package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test04 extends PosixParser_ESTest_scaffolding {

    private static final int ARGUMENT_COUNT = 38;
    private static final int DASH_ARGUMENT_INDEX = 17;
    private static final int NULL_SHORT_OPTION_INDEX = 27;
    private static final int NULL_DESCRIPTION_INDEX = 1;
    private static final String TRIPLE_DASH = "---";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        String[] arguments = new String[ARGUMENT_COUNT];
        arguments[DASH_ARGUMENT_INDEX] = TRIPLE_DASH;

        Options options = new Options();
        Option optionWithNullShortName =
                new Option(arguments[NULL_SHORT_OPTION_INDEX], TRIPLE_DASH, false, arguments[NULL_DESCRIPTION_INDEX]);
        Options optionsContainingNullShortName = options.addOption(optionWithNullShortName);

        PosixParser parser = new PosixParser();
        // The sparse argument array reproduces the EvoSuite-discovered NullPointerException path.
        try {
            parser.parse(optionsContainingNullShortName, arguments);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
