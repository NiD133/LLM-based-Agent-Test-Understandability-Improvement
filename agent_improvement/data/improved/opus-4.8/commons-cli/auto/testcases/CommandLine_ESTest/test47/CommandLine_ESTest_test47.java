package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test47 extends CommandLine_ESTest_scaffolding {

    /**
     * When an option group's selected option was never parsed into the command line,
     * {@link CommandLine#getParsedOptionValues(OptionGroup)} has no values to convert
     * and therefore returns {@code null}.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesForUnparsedSelectedOptionReturnsNull() throws Throwable {
        // An empty command line: no options have been parsed into it.
        CommandLine commandLine = new CommandLine();

        // Build an option group whose selected option is not present in the command line.
        Option selectedOption = new Option("@", "@");
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        Option[] parsedValues = commandLine.getParsedOptionValues(optionGroup);

        assertNull(parsedValues);
    }
}
