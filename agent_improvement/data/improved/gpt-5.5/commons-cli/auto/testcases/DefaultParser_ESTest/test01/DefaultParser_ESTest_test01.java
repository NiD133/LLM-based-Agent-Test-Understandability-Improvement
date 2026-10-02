package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test01 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        OptionGroup optionGroup = new OptionGroup();
        Option shortSOption = new Option("s", "s");

        OptionGroup configuredGroup = optionGroup.addOption(shortSOption);
        options.addOptionGroup(configuredGroup);

        String[] arguments = new String[3];
        arguments[0] = "-s";

        CommandLine commandLine = parser.parse(options, arguments, true);

        assertNotNull(commandLine);
    }
}
