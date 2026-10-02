package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing with stopAtNonOption=true returns a non-null CommandLine
     * even when the argument list contains an unrecognized option token.
     * The unrecognized token causes parsing to stop rather than throw an exception.
     */
    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // 14-element array; only the first slot holds an unrecognized option token
        String[] args = new String[14];
        args[0] = "-s-#\"J"; // unrecognized option with special characters

        // stopAtNonOption=true: parser stops gracefully at the unknown token instead of throwing
        CommandLine result = parser.parse(emptyOptions, args, true);
        assertNotNull(result);
    }
}
