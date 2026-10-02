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
public class DefaultParser_ESTest_test14 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing with stopAtNonOption = true should succeed even when the argument
     * array contains non-option tokens (here, "d" values) and trailing nulls.
     * Because no options are defined, every token is treated as a plain argument
     * and parsing returns a valid (non-null) CommandLine.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = DefaultParser.builder().get();

        // Six-element array with non-option tokens at indices 2 and 3; the rest are null.
        String[] arguments = new String[6];
        arguments[2] = "d";
        arguments[3] = "d";

        boolean stopAtNonOption = true;
        CommandLine commandLine = parser.parse(emptyOptions, arguments, stopAtNonOption);

        assertNotNull(commandLine);
    }
}
