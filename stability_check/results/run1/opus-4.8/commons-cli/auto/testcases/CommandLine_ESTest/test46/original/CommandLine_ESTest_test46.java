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

    @Test(timeout = 4000)
    public void test46() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        Option option0 = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);
        OptionGroup optionGroup0 = new OptionGroup();
        Option.Builder option_Builder0 = Option.builder();
        optionGroup0.setSelected(option0);
        // Undeclared exception!
        try {
            commandLine0.getParsedOptionValue(optionGroup0, (Supplier<Option>) option_Builder0);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
