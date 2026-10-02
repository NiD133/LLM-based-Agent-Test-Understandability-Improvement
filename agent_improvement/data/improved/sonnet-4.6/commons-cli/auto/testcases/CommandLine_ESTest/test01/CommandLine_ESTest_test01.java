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
     * Verifies that getParsedOptionValue wraps into a ParseException the exception thrown
     * by the default-value Supplier when the option has no value on the command line.
     *
     * The option being looked up (longOpt="Options") is not present on the empty CommandLine,
     * so the fallback Supplier is invoked. That Supplier is an Option.Builder configured with
     * a null opt and no longOpt; calling its get() method throws "Either opt or longOpt must
     * be specified", which getParsedOptionValue must re-throw as a ParseException.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Build an empty CommandLine (no options parsed)
        CommandLine.Builder builder = CommandLine.builder();
        CommandLine emptyCommandLine = builder.get();

        // Option to look up: short opt is null, longOpt is "Options" — not present on the command line
        Option targetOption = new Option((String) null, "Options");

        // Default-value Supplier that is itself broken: no opt or longOpt configured,
        // so calling its get() throws "Either opt or longOpt must be specified"
        Option.Builder brokenDefaultSupplier = Option.builder((String) null);

        // Because targetOption is absent, the fallback Supplier is called.
        // The Supplier throws, so getParsedOptionValue must propagate a ParseException.
        try {
            emptyCommandLine.getParsedOptionValue(targetOption, (Supplier<Option>) brokenDefaultSupplier);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // org.evosuite.runtime.mock.java.lang.MockThrowable: Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
