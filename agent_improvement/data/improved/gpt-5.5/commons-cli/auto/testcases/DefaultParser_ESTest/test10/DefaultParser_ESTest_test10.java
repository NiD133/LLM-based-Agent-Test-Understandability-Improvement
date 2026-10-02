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
public class DefaultParser_ESTest_test10 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final DefaultParser parser = new DefaultParser();
        final Options options = new Options();
        options.addOption("s", true, "-s");

        final String[] arguments = new String[6];
        arguments[0] = "-s";
        arguments[1] = "-";

        final CommandLine commandLine = parser.parse(options, arguments, true);

        assertNotNull(commandLine);
    }
}
