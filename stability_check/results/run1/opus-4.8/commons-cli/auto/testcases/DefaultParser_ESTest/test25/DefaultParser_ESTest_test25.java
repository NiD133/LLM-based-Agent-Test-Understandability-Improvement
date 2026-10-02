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

    /**
     * Parsing a short option that carries an inline argument value ("-s#")
     * should succeed and yield a non-null CommandLine, because option "s"
     * is declared as requiring an argument and "#" satisfies that argument.
     */
    @Test(timeout = 4000)
    public void parseShortOptionWithInlineArgumentReturnsCommandLine() throws Throwable {
        Options options = new Options();
        options.addOption("s", true, "s");

        String[] arguments = new String[10];
        arguments[0] = "-s#";

        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
