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
     * When the default-value supplier passed to
     * {@link CommandLine#getParsedOptionValue(OptionGroup, Supplier)} is an
     * incomplete {@link Option.Builder}, resolving the default value invokes the
     * builder, which rejects an option that specifies neither a short nor a long
     * name. The resulting {@link IllegalStateException} propagates to the caller.
     */
    @Test(timeout = 4000)
    public void test46() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // The selected option has no short name and a long name, so the group counts as selected.
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // An Option.Builder with no opt/longOpt configured, used as the default-value supplier.
        Supplier<Option> incompleteOptionSupplier = Option.builder();

        try {
            commandLine.getParsedOptionValue(optionGroup, incompleteOptionSupplier);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Option.builder().get() fails: "Either opt or longOpt must be specified".
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
