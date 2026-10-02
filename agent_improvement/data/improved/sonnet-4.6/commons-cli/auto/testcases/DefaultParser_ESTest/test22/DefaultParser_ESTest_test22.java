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
public class DefaultParser_ESTest_test22 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing succeeds when stopAtNonOption is true and the argument array
     * contains a single token that resembles a short option with an equals sign (e.g. "-=};SP'")
     * but does not match any registered option. With stopAtNonOption enabled, the parser
     * should stop at the unrecognized token and return a valid CommandLine rather than throwing.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        Options options = new Options();
        // Register an option with short key "js4" that accepts an argument
        options.addOption("js4", "", true, "-=};SP'");

        DefaultParser parser = new DefaultParser(true);

        // Build an argument array with 32 slots; only slot 23 contains a token
        String[] args = new String[32];
        args[23] = "-=};SP'";

        // Parse with stopAtNonOption=true: the unrecognized "-=};SP'" token causes the
        // parser to stop and collect remaining tokens as arguments rather than throwing
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
