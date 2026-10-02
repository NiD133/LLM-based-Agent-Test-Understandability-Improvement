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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CommandLine.Builder commandLineBuilder = CommandLine.builder();
        Option optionWithoutName = new Option((String) null, "Options");
        CommandLine commandLine = commandLineBuilder.get();
        Option.Builder fallbackOptionSupplier = Option.builder((String) null);

        try {
            commandLine.getParsedOptionValue(optionWithoutName, (Supplier<Option>) fallbackOptionSupplier);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // org.evosuite.runtime.mock.java.lang.MockThrowable: Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
