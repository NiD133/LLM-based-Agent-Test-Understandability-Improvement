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

    @Test(timeout = 4000)
    public void test_parseWithStopAtNonOption_returnsCommandLineForSparseArgArray() throws Throwable {
        Options options = new Options();
        DefaultParser parser = DefaultParser.builder().get();

        // Six-element array; only indices 2 and 3 are set to the non-option string "d"
        // (the remaining four slots stay null). With stopAtNonOption=true the parser
        // stops at the first unrecognised argument instead of throwing an exception.
        String[] args = new String[6];
        args[2] = "d";
        args[3] = "d";

        CommandLine commandLine = parser.parse(options, args, true);

        assertNotNull(commandLine);
    }
}
