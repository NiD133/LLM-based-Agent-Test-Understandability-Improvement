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
public class CommandLine_ESTest_test05 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CommandLine commandLine0 = new CommandLine();
        Option option0 = new Option((String) null, "mf", true, "&jM^W@]Ux%2T.zg ");
        option0.setArgs(320);
        commandLine0.addOption(option0);
        option0.processValue("&jM^W@]Ux%2T.zg ");
        option0.processValue("R({[");
        Properties properties0 = commandLine0.getOptionProperties(option0);
        assertEquals(1, properties0.size());
    }
}
