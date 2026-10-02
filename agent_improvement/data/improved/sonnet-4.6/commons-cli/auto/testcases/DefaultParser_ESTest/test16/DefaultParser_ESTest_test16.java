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
public class DefaultParser_ESTest_test16 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parseShortOptionWithInlineValue_stopAtNonOption_returnsCommandLine() throws Throwable {
        // Register a required option: short name "c", long name "-c=wt9", accepts argument, description "-c"
        Options options = new Options();
        Options optionsWithRequiredC = options.addRequiredOption("c", "-c=wt9", true, "-c");

        // Build the argument list: "-c=wt9" provides both the option key and its value inline
        String[] args = new String[2];
        args[0] = "-c=wt9";

        // Parse with stopAtNonOption=true: unrecognised tokens stop parsing rather than throwing
        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(optionsWithRequiredC, args, true);

        assertNotNull(commandLine);
    }
}
