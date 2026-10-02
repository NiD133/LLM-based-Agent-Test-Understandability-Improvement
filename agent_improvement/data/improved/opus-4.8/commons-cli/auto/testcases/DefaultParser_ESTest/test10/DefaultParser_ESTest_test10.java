package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test10 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses the option "-s" followed by a bare hyphen "-" used as its argument
     * value, with stopAtNonOption enabled. The parser should accept the input and
     * return a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseShortOptionWithHyphenArgumentReturnsCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Define a single short option "-s" that requires an argument.
        Options options = new Options();
        options.addOption("s", true, "-s");

        // Arguments: "-s" takes the following "-" as its value; trailing nulls are ignored.
        String[] arguments = new String[6];
        arguments[0] = "-s";
        arguments[1] = "-";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
