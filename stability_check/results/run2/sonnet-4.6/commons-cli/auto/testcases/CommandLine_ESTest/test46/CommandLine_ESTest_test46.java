package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test46 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that calling getParsedOptionValue with an OptionGroup whose selected option is set,
     * and a default-value Supplier that is an incomplete Option.Builder (no opt or longOpt configured),
     * throws IllegalStateException when the builder is invoked as the fallback supplier.
     *
     * Flow: the selected option has no value in the command line, so the default supplier is called;
     * building an Option from a builder with neither opt nor longOpt set triggers the exception.
     */
    @Test(timeout = 4000)
    public void test46() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Option with only a longOpt set; no value will be present in commandLine
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // Builder with no opt or longOpt — calling get() on it will throw IllegalStateException
        Option.Builder incompleteOptionBuilder = Option.builder();

        try {
            commandLine.getParsedOptionValue(optionGroup, (Supplier<Option>) incompleteOptionBuilder);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // "Either opt or longOpt must be specified"
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
