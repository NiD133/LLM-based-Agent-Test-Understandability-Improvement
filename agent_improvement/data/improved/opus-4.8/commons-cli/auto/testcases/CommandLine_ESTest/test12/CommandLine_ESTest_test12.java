package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test12 extends CommandLine_ESTest_scaffolding {

    /**
     * When a queried option has no parsed argument value, {@code getOptionValue(Option, String)}
     * should fall back to the supplied default value.
     */
    @Test(timeout = 4000)
    public void getOptionValueReturnsDefaultWhenOptionHasNoValue() throws Throwable {
        // Build a CommandLine that contains an option but no argument values for it.
        Option presentOption = new Option("cL", "cL");
        CommandLine commandLine = CommandLine.builder()
                .setDeprecatedHandler((Consumer<Option>) null)
                .get();
        commandLine.addOption(presentOption);

        // Query the option: it has no value, so the default "cL" must be returned.
        Option queriedOption = Option.builder("cL").deprecated().get();
        String defaultValue = "cL";

        String value = commandLine.getOptionValue(queriedOption, defaultValue);

        assertEquals("cL", value);
    }
}
