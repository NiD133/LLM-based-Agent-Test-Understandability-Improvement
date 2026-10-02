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

    /**
     * An option that carries no argument values should yield an empty
     * {@link Properties} map when looked up via {@link CommandLine#getOptionProperties(String)}.
     */
    @Test(timeout = 4000)
    public void getOptionPropertiesReturnsEmptyWhenOptionHasNoValues() throws Throwable {
        // Option with short name "1" and description "1", but no parsed values.
        Option optionWithoutValues = new Option("1", "1");

        CommandLine commandLine = new CommandLine();
        commandLine.addOption(optionWithoutValues);

        Properties properties = commandLine.getOptionProperties("1");

        assertEquals(0, properties.size());
    }
}
