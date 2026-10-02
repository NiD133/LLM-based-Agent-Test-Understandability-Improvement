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
public class DefaultParser_ESTest_test18 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        Options emptyOptions = new Options();
        Options optionsWithRequiredArgument = emptyOptions.addRequiredOption("s4", "s4", true, "-s4");
        DefaultParser parser = new DefaultParser();

        String[] commandLineWithMissingArgument = new String[3];
        commandLineWithMissingArgument[0] = "-s4";

        try {
            parser.parse(optionsWithRequiredArgument, commandLineWithMissingArgument, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s4
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
