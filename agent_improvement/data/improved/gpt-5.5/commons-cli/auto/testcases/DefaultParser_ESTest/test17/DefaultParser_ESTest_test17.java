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
public class DefaultParser_ESTest_test17 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        Options noRecognizedOptions = new Options();
        DefaultParser parser = new DefaultParser();
        String[] arguments = new String[2];
        arguments[0] = "-c=wt9";

        CommandLine parsedCommandLine = parser.parse(noRecognizedOptions, arguments, true);

        assertNotNull(parsedCommandLine);
    }
}
