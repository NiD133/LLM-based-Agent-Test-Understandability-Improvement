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

    /**
     * Verifies that getParsedOptionValues wraps a ClassCastException in a ParseException
     * when the option's declared type (Option.class) is incompatible with its stored
     * string value ("D").
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Create an option whose converter will try to cast a String to Option, which must fail
        Option option = new Option("D", "D", true, "D");
        Class<Option> optionClass = Option.class;
        option.setType(optionClass);
        commandLine.addOption(option);
        option.processValue("D");

        // Mock supplier used as default-value fallback; it is never invoked because parsing fails first
        Supplier<Class<Option>[]> defaultValueSupplier =
                (Supplier<Class<Option>[]>) mock(Supplier.class, new ViolatedAssumptionAnswer());

        try {
            commandLine.getParsedOptionValues(option, defaultValueSupplier);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // String "D" cannot be cast to Option, so a ParseException wrapping ClassCastException is thrown
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
