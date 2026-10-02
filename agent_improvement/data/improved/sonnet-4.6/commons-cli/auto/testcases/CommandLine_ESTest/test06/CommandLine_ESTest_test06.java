package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test06 extends CommandLine_ESTest_scaffolding {

    /**
     * Verifies that getOptionProperties returns exactly one entry when the option
     * has a single processed value. A lone value is treated as a property key
     * whose corresponding value defaults to "true".
     */
    @Test(timeout = 4000)
    public void test_getOptionProperties_withSingleValue_returnsOneEntry() throws Throwable {
        CommandLine commandLine = new CommandLine();

        // Option with no short name, long name "mf", requires an argument
        Option option = new Option((String) null, "mf", true, "2&jM^W@]Ux%2T.zg ");
        commandLine.addOption(option);
        option.processValue("2&jM^W@]Ux%2T.zg ");

        Properties properties = commandLine.getOptionProperties(option);

        assertEquals(1, properties.size());
    }
}
