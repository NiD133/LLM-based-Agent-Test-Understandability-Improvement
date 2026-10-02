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
public class DefaultParser_ESTest_test26 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses a single short option "s" that is followed by extra characters
     * ("-s-#\"J"). With stopAtNonOption enabled, the parser recognizes the
     * leading "-s" flag and treats the remaining characters as the option's
     * argument value, returning a non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void parseShortOptionWithTrailingCharactersReturnsCommandLine() throws Throwable {
        // Define a single flag option "s" (takes no argument).
        Options options = new Options();
        options.addOption("s", false, "s");

        // First argument starts with "-s" followed by extra characters; the
        // remaining slots are left null (unused).
        String[] arguments = new String[14];
        arguments[0] = "-s-#\"J";

        DefaultParser parser = new DefaultParser();
        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(options, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
