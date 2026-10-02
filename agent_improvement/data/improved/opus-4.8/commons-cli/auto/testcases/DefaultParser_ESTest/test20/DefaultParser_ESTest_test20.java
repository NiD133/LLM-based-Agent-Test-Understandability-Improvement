package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test20 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that a required option is satisfied via partial long-option matching.
     *
     * <p>The options define a required long option "s4". The arguments contain the
     * token "--s", which partially matches "s4" (partial matching is enabled by the
     * default constructor). Because the required option is therefore considered
     * present, parsing succeeds and returns a non-null {@link CommandLine} rather than
     * throwing a {@code MissingOptionException}.</p>
     */
    @Test(timeout = 4000)
    public void parseSatisfiesRequiredOptionByPartialLongMatch() throws Throwable {
        // Define a single required option whose short and long name are both "s4".
        Options options = new Options();
        options.addRequiredOption("s4", "s4", false, "--s");

        // Arguments: only the last token is meaningful; "--s" partially matches "s4".
        String[] arguments = new String[3];
        arguments[2] = "--s";

        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
