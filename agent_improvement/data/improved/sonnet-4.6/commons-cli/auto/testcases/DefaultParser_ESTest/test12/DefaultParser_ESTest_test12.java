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
public class DefaultParser_ESTest_test12 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parseLoneHyphenWithStopAtNonOption_returnsCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();
        // A lone "-" is not a recognized option flag; with stopAtNonOption=true it is
        // treated as a plain argument rather than causing an UnrecognizedOptionException.
        String[] args = new String[] { "-" };
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
