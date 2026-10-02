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
     * Verifies that when {@link CommandLine#getParsedOptionValue(OptionGroup, Supplier)}
     * falls back to its default-value supplier, any exception thrown while evaluating
     * that supplier propagates to the caller.
     *
     * <p>The option group is selected but no matching option is registered on the
     * command line, so the parsed value is null and the default-value supplier is
     * invoked. Here the supplier is an empty {@link Option.Builder}, whose
     * {@link Option.Builder#get()} fails with an {@link IllegalStateException}
     * because neither {@code opt} nor {@code longOpt} was set.</p>
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuePropagatesSupplierFailure() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Select an option in the group so the command line looks it up (and finds nothing).
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // An empty builder used as the default-value supplier; its get() throws when evaluated.
        Supplier<Option> defaultValueSupplier = Option.builder();

        try {
            commandLine.getParsedOptionValue(optionGroup, defaultValueSupplier);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Either opt or longOpt must be specified
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
