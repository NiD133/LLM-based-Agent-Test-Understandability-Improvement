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
public class DefaultParser_ESTest_test14 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser.Builder parserBuilder = DefaultParser.builder();
        DefaultParser parser = parserBuilder.get();

        String[] arguments = new String[6];
        arguments[2] = "d";
        arguments[3] = "d";

        CommandLine parsedCommandLine = parser.parse(emptyOptions, arguments, true);
        assertNotNull(parsedCommandLine);
    }
}
