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
public class DefaultParser_ESTest_test17 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an unrecognized short option with an inline value ("-c=wt9")
     * against an empty Options set succeeds when stopAtNonOption is true.
     * With stopAtNonOption=true, unrecognized tokens are collected as arguments
     * rather than causing a ParseException.
     */
    @Test(timeout = 4000)
    public void test_parseWithStopAtNonOption_unrecognizedShortOptionWithValue_returnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Two-element array: first is the unrecognized token, second is null (ignored during parse)
        String[] args = new String[2];
        args[0] = "-c=wt9";

        CommandLine result = parser.parse(emptyOptions, args, true);

        assertNotNull(result);
    }
}
