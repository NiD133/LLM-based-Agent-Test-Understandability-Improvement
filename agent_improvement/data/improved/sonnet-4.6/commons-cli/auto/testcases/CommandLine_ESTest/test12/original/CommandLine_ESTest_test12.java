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
public class CommandLine_ESTest_test12 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Option option0 = new Option("cL", "cL");
        CommandLine.Builder commandLine_Builder0 = CommandLine.builder();
        CommandLine.Builder commandLine_Builder1 = commandLine_Builder0.setDeprecatedHandler((Consumer<Option>) null);
        CommandLine commandLine0 = commandLine_Builder1.get();
        commandLine0.addOption(option0);
        Option.Builder option_Builder0 = Option.builder("cL");
        Option.Builder option_Builder1 = option_Builder0.deprecated();
        Option option1 = option_Builder1.get();
        String string0 = commandLine0.getOptionValue(option1, "cL");
        assertEquals("cL", string0);
    }
}
