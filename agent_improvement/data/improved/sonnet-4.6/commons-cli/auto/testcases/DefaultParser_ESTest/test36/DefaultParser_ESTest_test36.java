package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test36 extends DefaultParser_ESTest_scaffolding {

    /**
     * When stopAtNonOption is true, an unrecognized short option does not throw;
     * parsing stops and the remaining tokens (including nulls) are left as-is,
     * and a valid CommandLine is still returned.
     */
    @Test(timeout = 4000)
    public void test_parseWithStopAtNonOption_unrecognizedShortOption_returnsCommandLine() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Seven-element array; only the first slot is set — nulls are skipped during token handling
        String[] args = new String[7];
        args[0] = "-s";

        CommandLine result = parser.parse(emptyOptions, args, true);

        assertNotNull(result);
    }
}
