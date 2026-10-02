package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test25 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses a short option whose value is attached directly to it ("-s#"),
     * where the option "s" is defined as requiring an argument. The parser
     * should accept the concatenated value and return a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parsesShortOptionWithAttachedArgument() throws Throwable {
        // Define a single short option "s" that requires an argument.
        Options options = new Options();
        options.addOption("s", true, "s");

        // Argument "-s#" means: option "s" with the attached value "#".
        // Only the first slot is used; the remaining slots stay null.
        String[] arguments = new String[10];
        arguments[0] = "-s#";

        // Parse with stopAtNonOption = false.
        DefaultParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
