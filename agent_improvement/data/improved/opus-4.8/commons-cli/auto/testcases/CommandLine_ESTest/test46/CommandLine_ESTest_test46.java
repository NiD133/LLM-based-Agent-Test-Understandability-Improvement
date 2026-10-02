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
     * When {@code getParsedOptionValue} is asked for the value of a selected option group
     * whose selected option holds no value, it falls back to the supplied default-value
     * {@link Supplier}. Here the supplier is an empty {@link Option.Builder}: invoking it
     * builds an Option with neither a short nor a long name, which is illegal, so the call
     * surfaces the builder's {@link IllegalStateException}.
     */
    @Test(timeout = 4000)
    public void parsedOptionValue_whenDefaultSupplierBuildsInvalidOption_throwsIllegalState() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // An option identified only by its long name; it carries no parsed value.
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setSelected(selectedOption);

        // The default-value supplier is an empty builder with no opt/longOpt set,
        // so building an Option from it fails.
        Supplier<Option> emptyBuilderSupplier = Option.builder();

        try {
            commandLine.getParsedOptionValue(optionGroup, emptyBuilderSupplier);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Thrown by Option: "Either opt or longOpt must be specified"
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
