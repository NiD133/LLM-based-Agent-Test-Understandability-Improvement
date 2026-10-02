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
public class DefaultParser_ESTest_test30 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing succeeds when a required option is actually supplied on the
     * command line: the required "s4" option is satisfied by the "--s4"
     * argument, so no MissingOptionException is thrown and a CommandLine is
     * returned.
     */
    @Test(timeout = 4000)
    public void parseSucceedsWhenRequiredLongOptionIsProvided() throws Throwable {
        // An Options set with a single required option (short and long name "s4").
        Options options = new Options();
        options.addRequiredOption("s4", "s4", false, "--s4");

        DefaultParser parser = new DefaultParser(false);

        // Command line arguments containing the required option "--s4".
        String[] arguments = new String[6];
        arguments[1] = "--s4";

        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
