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
        CommandLine commandLine = new CommandLine();
        Option selectedOption = new Option((String) null, ")*+k_w|'lpI0SM", false, (String) null);
        OptionGroup selectedOptionGroup = new OptionGroup();
        Option.Builder defaultOptionSupplier = Option.builder();

        selectedOptionGroup.setSelected(selectedOption);

        try {
            commandLine.getParsedOptionValue(selectedOptionGroup, (Supplier<Option>) defaultOptionSupplier);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.Option", e);
        }
    }
}
