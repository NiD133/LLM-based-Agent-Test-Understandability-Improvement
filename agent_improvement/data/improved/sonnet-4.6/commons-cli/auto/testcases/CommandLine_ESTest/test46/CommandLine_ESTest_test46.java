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
public class CommandLine_ESTest_test46 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getParsedOptionValue(OptionGroup, Supplier) propagates an
     * IllegalStateException thrown by the default-value Supplier when the selected
     * option has no parsed value.
     *
     * The OptionGroup has a selected option (so the group is "active"), but that
     * option was never added to the CommandLine, so its parsed value is null.
     * The fallback Supplier is an Option.Builder with no opt/longOpt configured;
     * calling get() on it throws IllegalStateException("Either opt or longOpt
     * must be specified"), which should propagate out of getParsedOptionValue.
     */
    @Test(timeout = 4000)
    public void test46() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // An option that is "selected" in the group but not present in the command line.
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // A builder with no opt/longOpt: calling get() throws IllegalStateException.
        Option.Builder incompleteBuilder = Option.builder();

        try {
            commandLine.getParsedOptionValue(optionGroup, (Supplier<Option>) incompleteBuilder);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
