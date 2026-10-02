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
public class DefaultParser_ESTest_test25 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        Options configuredOptions = new Options();
        DefaultParser parser = new DefaultParser();
        String[] arguments = new String[10];
        arguments[0] = "-s#";

        Options optionsWithArgument = configuredOptions.addOption("s", true, "s");
        CommandLine parsedCommandLine = parser.parse(optionsWithArgument, arguments, false);

        assertNotNull(parsedCommandLine);
    }
}
