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

    /**
     * Verifies that {@link CommandLine#getParsedOptionValues(Option, Supplier)} returns
     * the supplier-provided default when the requested option is not present on the command line.
     *
     * The command line only holds an option whose short and long names are both "GPmL", while the
     * queried option (built separately and marked deprecated) has short name "GPmL" but no long name.
     * Since these two options are not equal, no values are found, so the method falls back to the
     * default value supplier. Here the supplier is {@code null}, which the CUT treats as "default to null".
     */
    @Test(timeout = 4000)
    public void parsedOptionValuesReturnsNullWhenOptionAbsentAndDefaultSupplierIsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option presentOption = new Option("GPmL", "GPmL");
        commandLine.addOption(presentOption);

        Option queriedOption = Option.builder("GPmL").deprecated().get();

        Option[] parsedValues = commandLine.getParsedOptionValues(queriedOption, (Supplier<Option[]>) null);

        assertNull(parsedValues);
    }
}
