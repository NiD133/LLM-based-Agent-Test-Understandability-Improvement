package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test47 extends CommandLine_ESTest_scaffolding {

    /**
     * getParsedOptionValues returns null when the option group's selected option
     * was never added to (parsed into) the CommandLine.
     */
    @Test(timeout = 4000)
    public void test_getParsedOptionValues_optionGroupSelectedButNotParsed_returnsNull() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Create an option and mark it as the selected option in a group,
        // but never add it to the CommandLine (simulating an unparsed option).
        Option selectedOption = new Option("@", "@");
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // Since the option was never parsed into the CommandLine, the result must be null.
        Option[] parsedValues = commandLine.getParsedOptionValues(optionGroup);
        assertNull(parsedValues);
    }
}
