package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.Supplier;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test00 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CommandLine commandLine = new CommandLine();
        Option typedOption = new Option("D", "D", true, "D");

        typedOption.setType(Option.class);
        commandLine.addOption(typedOption);
        typedOption.processValue("D");

        @SuppressWarnings("unchecked")
        Supplier<Class<Option>[]> defaultValues = (Supplier<Class<Option>[]>) mock(Supplier.class, new ViolatedAssumptionAnswer());

        try {
            commandLine.getParsedOptionValues(typedOption, defaultValues);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            // The option converter returns the raw string value, which cannot be cast to Option.class.
            verifyException("org.apache.commons.cli.ParseException", exception);
        }
    }
}
