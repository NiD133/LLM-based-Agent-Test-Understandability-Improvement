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
public class DefaultParser_ESTest_test36 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36() throws Throwable {
        final Options declaredOptions = new Options();
        final DefaultParser parser = new DefaultParser();
        final String[] commandLineTokens = new String[7];
        commandLineTokens[0] = "-s";

        final CommandLine parsedCommandLine = parser.parse(declaredOptions, commandLineTokens, true);

        assertNotNull(parsedCommandLine);
    }
}
