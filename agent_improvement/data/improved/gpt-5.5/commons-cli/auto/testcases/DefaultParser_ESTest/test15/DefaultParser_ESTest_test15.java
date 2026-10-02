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
public class DefaultParser_ESTest_test15 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Options baseOptions = new Options();
        Options requiredShortOption = baseOptions.addRequiredOption("c", "-c=wt9", false, "-c");
        DefaultParser parserWithoutPartialMatching = new DefaultParser(false);
        String[] commandLineArguments = new String[2];
        commandLineArguments[0] = "-c=wt9";

        try {
            parserWithoutPartialMatching.parse(requiredShortOption, commandLineArguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -c=wt9
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
