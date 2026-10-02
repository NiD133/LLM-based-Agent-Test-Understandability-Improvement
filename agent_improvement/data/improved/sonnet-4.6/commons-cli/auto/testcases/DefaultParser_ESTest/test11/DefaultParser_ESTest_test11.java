package org.apache.commons.cli;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test11 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that when nonOptionAction is IGNORE, handleUnknownToken() silently
     * discards an unrecognized option-like token ("-T") without throwing any exception.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        DefaultParser parser = new DefaultParser();
        parser.nonOptionAction = DefaultParser.NonOptionAction.IGNORE;

        // "-T" starts with "-" and is unrecognized; with IGNORE action no exception is thrown
        parser.handleUnknownToken("-T");
    }
}
