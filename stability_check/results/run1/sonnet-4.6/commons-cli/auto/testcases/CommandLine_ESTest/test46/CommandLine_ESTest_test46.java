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
     * Verifies that getParsedOptionValue(OptionGroup, Supplier) throws IllegalStateException
     * when the default-value Supplier is an Option.Builder with neither opt nor longOpt set.
     *
     * Flow: the OptionGroup has a selected option that carries no value in the CommandLine,
     * so the method falls back to the Supplier. Calling get() on an unconfigured Option.Builder
     * raises "Either opt or longOpt must be specified".
     */
    @Test(timeout = 4000)
    public void test46() throws Throwable {
        // A CommandLine with no parsed options or arguments
        CommandLine commandLine = new CommandLine();

        // An option with no short name and a long name; it is selected in the group below
        // but was never added to the CommandLine, so its value will be absent
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);

        // An OptionGroup whose selected option is the one above
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // An Option.Builder with no opt or longOpt configured.
        // When used as a Supplier<Option>, calling get() throws IllegalStateException.
        Option.Builder incompleteBuilder = Option.builder();

        // getParsedOptionValue finds the selected option has no value in the CommandLine
        // and invokes the Supplier fallback, which triggers the IllegalStateException
        // from the unconfigured builder.
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
