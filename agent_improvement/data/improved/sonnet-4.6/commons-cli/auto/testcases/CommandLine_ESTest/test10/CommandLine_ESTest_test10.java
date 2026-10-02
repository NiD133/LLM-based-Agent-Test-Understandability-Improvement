package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test10 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that hasOption(Option) returns true when the command line contains an option
     * matching by key, even if the queried Option instance is marked as deprecated.
     * Option equality is key-based, so a deprecated option with the same short name "n"
     * is found in the command line that holds a non-deprecated option with the same key.
     */
    @Test(timeout = 4000)
    public void test_hasOptionWithDeprecatedInstance_matchesByKey() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option optionN = new Option("n", "n");
        commandLine.addOption(optionN);

        Option.Builder builder = Option.builder("n");
        builder.deprecated();
        Option deprecatedOptionN = builder.get();

        boolean found = commandLine.hasOption(deprecatedOptionN);

        assertTrue(found);
    }
}
