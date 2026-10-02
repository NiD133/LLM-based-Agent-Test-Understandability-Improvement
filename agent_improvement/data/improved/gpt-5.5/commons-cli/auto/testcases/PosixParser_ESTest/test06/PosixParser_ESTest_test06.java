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
public class PosixParser_ESTest_test06 extends PosixParser_ESTest_scaffolding {

    private static final String FIRST_ARGUMENT = "i}=ILQ<";
    private static final int ORIGINAL_ARGUMENT_COUNT = 6;
    private static final int STOP_AT_NON_OPTION_RESULT_COUNT = 7;
    private static final int KEEP_PARSING_RESULT_COUNT = 2;

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options options = new Options();
        String[] originalArguments = new String[ORIGINAL_ARGUMENT_COUNT];
        originalArguments[0] = FIRST_ARGUMENT;

        String[] stoppedAtNonOption = parser.flatten(options, originalArguments, true);
        String[] continuedParsing = parser.flatten(options, stoppedAtNonOption, false);

        assertEquals(KEEP_PARSING_RESULT_COUNT, continuedParsing.length);
        assertEquals(STOP_AT_NON_OPTION_RESULT_COUNT, stoppedAtNonOption.length);
    }
}
