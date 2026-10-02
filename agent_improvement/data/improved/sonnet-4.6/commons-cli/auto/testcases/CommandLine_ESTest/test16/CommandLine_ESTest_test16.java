package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test16 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_getParsedOptionValues_deprecatedOptionWithNullDefaultSupplier_returnsNull() throws Throwable {
        // Add a non-deprecated option "GPmL" (with no values) to the command line
        CommandLine commandLine = new CommandLine();
        Option parsedOption = new Option("GPmL", "GPmL");
        commandLine.addOption(parsedOption);

        // Build a deprecated variant of the same option by short name
        Option deprecatedOption = Option.builder("GPmL")
                .deprecated()
                .get();

        // The matched option has no values and the default supplier is null, so the result is null
        Option[] result = commandLine.getParsedOptionValues(deprecatedOption, (Supplier<Option[]>) null);
        assertNull(result);
    }
}
