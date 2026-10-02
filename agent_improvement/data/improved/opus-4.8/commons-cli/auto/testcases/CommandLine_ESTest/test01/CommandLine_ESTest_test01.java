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
public class CommandLine_ESTest_test01 extends CommandLine_ESTest_scaffolding {

    /**
     * When {@code getParsedOptionValue(Option, Supplier)} finds no value for the option,
     * it falls back to the supplied default value. Here the default-value supplier is an
     * incompletely configured {@link Option.Builder} whose {@code get()} fails because
     * neither a short nor a long option was specified. {@code CommandLine} wraps that
     * failure in a {@link ParseException}.
     */
    @Test(timeout = 4000)
    public void getParsedOptionValueWrapsFailingDefaultSupplierInParseException() throws Throwable {
        // An empty command line: it holds no parsed options, so any lookup yields no value.
        CommandLine emptyCommandLine = CommandLine.builder().get();

        // The option being queried; it is absent from the command line above.
        Option missingOption = new Option((String) null, "Options");

        // Default-value supplier that is itself broken: building an Option from a builder
        // created with a null option name throws "Either opt or longOpt must be specified".
        Option.Builder failingDefaultValueSupplier = Option.builder((String) null);

        try {
            emptyCommandLine.getParsedOptionValue(missingOption, (Supplier<Option>) failingDefaultValueSupplier);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // The supplier's failure is rethrown wrapped as a ParseException.
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
