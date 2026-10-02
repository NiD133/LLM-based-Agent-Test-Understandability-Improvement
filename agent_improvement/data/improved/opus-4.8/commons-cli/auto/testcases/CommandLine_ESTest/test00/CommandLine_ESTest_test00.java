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
     * Verifies that {@link CommandLine#getParsedOptionValues(Option, Supplier)} throws a
     * {@link ParseException} when the parsed value cannot be converted to the option's declared type.
     *
     * <p>The option declares its type as {@code Option.class}, but its only value is the plain
     * string {@code "D"}. Converting that string and casting it to {@code Option} raises a
     * {@link ClassCastException} internally, which the method wraps and rethrows as a
     * {@link ParseException}.</p>
     */
    @Test(timeout = 4000)
    public void getParsedOptionValuesThrowsWhenValueCannotBeCastToOptionType() throws Throwable {
        // An option named "D" that accepts an argument and declares its type as Option.class.
        Option optionWithOptionType = new Option("D", "D", true, "D");
        optionWithOptionType.setType(Option.class);

        // Register the option on the command line and give it the string value "D".
        CommandLine commandLine = new CommandLine();
        commandLine.addOption(optionWithOptionType);
        optionWithOptionType.processValue("D");

        // The default-value supplier is never consulted here because the option does have a value;
        // a strict mock makes that explicit (any use would fail the test).
        @SuppressWarnings("unchecked")
        Supplier<Class<Option>[]> unusedDefaultValueSupplier =
                (Supplier<Class<Option>[]>) mock(Supplier.class, new ViolatedAssumptionAnswer());

        try {
            commandLine.getParsedOptionValues(optionWithOptionType, unusedDefaultValueSupplier);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // The underlying ClassCastException ("Cannot cast java.lang.String to
            // org.apache.commons.cli.Option") is wrapped in a ParseException.
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
