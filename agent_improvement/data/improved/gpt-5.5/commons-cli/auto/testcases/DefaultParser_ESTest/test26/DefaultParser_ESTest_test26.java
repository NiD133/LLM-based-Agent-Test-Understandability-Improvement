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
public class DefaultParser_ESTest_test26 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        final Options options = new Options();
        options.addOption("s", false, "s");

        final String[] arguments = new String[14];
        arguments[0] = "-s-#\"J";

        final DefaultParser parser = new DefaultParser();
        final CommandLine parsedCommandLine = parser.parse(options, arguments, true);

        assertNotNull(parsedCommandLine);
    }
}
