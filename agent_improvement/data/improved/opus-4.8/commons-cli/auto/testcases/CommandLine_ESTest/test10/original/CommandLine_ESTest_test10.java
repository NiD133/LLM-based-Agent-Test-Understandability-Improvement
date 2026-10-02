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
public class CommandLine_ESTest_test10 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        Option option0 = new Option("n", "n");
        commandLine0.addOption(option0);
        Option.Builder option_Builder0 = Option.builder("n");
        option_Builder0.deprecated();
        Option option1 = option_Builder0.get();
        boolean boolean0 = commandLine0.hasOption(option1);
        assertTrue(boolean0);
    }
}
