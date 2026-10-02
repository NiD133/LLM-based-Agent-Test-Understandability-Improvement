package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test08 extends DefaultParser_ESTest_scaffolding {

    private static final String OPTION_REQUIRING_ARGUMENT = "s";

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        options.addOption(OPTION_REQUIRING_ARGUMENT, true, "-s");

        String[] commandLineTokens = new String[7];
        commandLineTokens[0] = "-s";
        commandLineTokens[1] = "-s";

        try {
            parser.parse(options, commandLineTokens, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
