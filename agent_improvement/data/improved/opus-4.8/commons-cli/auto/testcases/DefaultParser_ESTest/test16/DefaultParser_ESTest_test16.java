package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test16 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses a command line where the required short option "c" (which takes an
     * argument) is supplied using the "-c=value" syntax. Parsing should succeed
     * and return a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseRequiredShortOptionWithEqualsValue_returnsCommandLine() throws Throwable {
        // Define a required option "c" that accepts an argument.
        Options options = new Options();
        options.addRequiredOption("c", "-c=wt9", true, "-c");

        DefaultParser parser = new DefaultParser();

        // Supply the option as "-c=wt9"; the trailing null argument is ignored.
        String[] arguments = new String[2];
        arguments[0] = "-c=wt9";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
