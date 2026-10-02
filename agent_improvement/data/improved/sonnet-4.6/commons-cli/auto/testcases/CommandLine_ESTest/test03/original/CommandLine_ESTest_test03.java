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
public class CommandLine_ESTest_test03 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        CommandLine.Builder commandLine_Builder0 = CommandLine.builder();
        Option option0 = new Option((String) null, "Options");
        CommandLine.Builder commandLine_Builder1 = commandLine_Builder0.addOption(option0);
        CommandLine commandLine0 = commandLine_Builder1.get();
        int int0 = commandLine0.getOptionCount('h');
        assertEquals(0, int0);
    }
}
