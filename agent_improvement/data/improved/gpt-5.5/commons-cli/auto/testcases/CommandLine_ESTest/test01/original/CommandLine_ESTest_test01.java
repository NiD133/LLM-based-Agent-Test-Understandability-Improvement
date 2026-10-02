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
        CommandLine.Builder commandLine_Builder0 = CommandLine.builder();
        Option option0 = new Option((String) null, "Options");
        CommandLine commandLine0 = commandLine_Builder0.get();
        Option.Builder option_Builder0 = Option.builder((String) null);
        try {
            commandLine0.getParsedOptionValue(option0, (Supplier<Option>) option_Builder0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // org.evosuite.runtime.mock.java.lang.MockThrowable: Either opt or longOpt must be specified
            //
            verifyException("org.apache.commons.cli.ParseException", e);
        }
    }
}
