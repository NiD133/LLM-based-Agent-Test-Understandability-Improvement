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
     * Verifies that {@link CommandLine#hasOption(Option)} reports {@code true}
     * when a matching option is present on the command line.
     *
     * <p>The added option and the queried option are considered equal because
     * they share the same short name {@code "n"}, even though the queried option
     * was created through a different builder and is flagged as deprecated.</p>
     */
    @Test(timeout = 4000)
    public void hasOptionReturnsTrueForMatchingOption() throws Throwable {
        // Register an option with short name "n" on the command line.
        CommandLine commandLine = new CommandLine();
        Option registeredOption = new Option("n", "n");
        commandLine.addOption(registeredOption);

        // Build a separate, deprecated option that also uses short name "n".
        Option deprecatedOption = Option.builder("n").deprecated().get();

        // The command line should recognize the equivalent option.
        boolean optionPresent = commandLine.hasOption(deprecatedOption);

        assertTrue(optionPresent);
    }
}
