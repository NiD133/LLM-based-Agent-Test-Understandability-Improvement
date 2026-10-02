package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test17 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that {@link CommandLine#getOptionValue(Option, String)} returns the
     * supplied default value when the queried option is not present on the command line.
     *
     * <p>The command line only knows about an option that has a short name but no long
     * option ({@code new Option("GPmL", "GPmL")}). The option used for the lookup, however,
     * also declares a long option, so the two are not considered equal. Because no matching
     * option is found, the default value ("GPmL") is returned.</p>
     */
    @Test(timeout = 4000)
    public void getOptionValueReturnsDefaultWhenOptionNotPresent() throws Throwable {
        // Command line containing an option whose short name is "GPmL" (and no long option).
        CommandLine commandLine = new CommandLine();
        Option storedOption = new Option("GPmL", "GPmL");
        commandLine.addOption(storedOption);

        // A different option: same short name but also a long option, so it is not equal
        // to the stored option and therefore not found on the command line.
        Option lookupOption = Option.builder("GPmL").longOpt("GPmL").get();

        String defaultValue = "GPmL";
        String value = commandLine.getOptionValue(lookupOption, defaultValue);

        // The option is absent, so the default value is returned unchanged.
        assertEquals("GPmL", value);
    }
}
