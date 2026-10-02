package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test10 extends PosixParser_ESTest_scaffolding {

    /**
     * Bursting an empty token is a no-op: burstToken iterates from index 1 to
     * token.length(), so an empty string skips the loop entirely. This means it
     * never touches the (here uninitialized) options field, and so completes
     * without throwing.
     */
    @Test(timeout = 4000)
    public void burstTokenWithEmptyTokenDoesNothingAndDoesNotThrow() throws Throwable {
        PosixParser parser = new PosixParser();

        parser.burstToken("", false);
    }
}
