package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test21 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21_getOptionPropertiesReturnsEmptyWhenOptionHasNoValues() throws Throwable {
        // An option added without values should yield an empty properties map
        CommandLine commandLine = new CommandLine();
        Option option = new Option("1", "1");
        commandLine.addOption(option);

        Properties properties = commandLine.getOptionProperties("1");

        assertEquals(0, properties.size());
    }
}
