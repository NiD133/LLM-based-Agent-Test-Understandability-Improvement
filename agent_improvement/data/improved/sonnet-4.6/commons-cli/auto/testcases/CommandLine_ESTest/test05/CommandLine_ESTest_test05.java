package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CommandLine_ESTest_test05 extends CommandLine_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Build a command line with a multi-value option (no short name, long name "mf")
        CommandLine commandLine = new CommandLine();
        Option multiValueOption = new Option((String) null, "mf", true, "&jM^W@]Ux%2T.zg ");
        multiValueOption.setArgs(320);
        commandLine.addOption(multiValueOption);

        // Process two values: getOptionProperties pairs them as one key-value property entry
        multiValueOption.processValue("&jM^W@]Ux%2T.zg ");
        multiValueOption.processValue("R({[");

        Properties properties = commandLine.getOptionProperties(multiValueOption);
        assertEquals(1, properties.size());
    }
}
