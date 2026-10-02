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
public class DefaultParser_ESTest_test07 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing a short option that accepts an argument succeeds when
     * stopAtNonOption is true and the option value is supplied as the next token.
     * The trailing null slot in the args array is silently skipped by the parser.
     */
    @Test(timeout = 4000)
    public void test07_parseShortOptionWithArgAndStopAtNonOption_returnsNonNullCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();

        Options options = new Options();
        // Register short option "s" that requires one argument
        options.addOption("s", true, "-=9Udvb/'--");

        // Build the argument list: the option flag followed by its value; index 2 stays null
        String[] args = new String[3];
        args[0] = "-s";
        args[1] = "-=9Udvb/'--";

        // Parse with stopAtNonOption=true; a valid CommandLine must be returned
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
