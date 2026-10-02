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
public class DefaultParser_ESTest_test13 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that the "--" end-of-options sentinel is handled correctly when no options
     * are defined and stopAtNonOption is false. The parser should return a valid CommandLine
     * without throwing an exception, because "--" is a recognized token that signals the
     * end of option processing rather than an unrecognized option.
     */
    @Test(timeout = 4000)
    public void test_parseEndOfOptionsSentinel_returnsNonNullCommandLine() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options emptyOptions = new Options();
        String[] argsWithEndOfOptionsMarker = new String[] { "--" };

        CommandLine result = parser.parse(emptyOptions, argsWithEndOfOptionsMarker, false);

        assertNotNull(result);
    }
}
