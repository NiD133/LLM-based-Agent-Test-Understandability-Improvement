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
     * burstToken skips its loop entirely when the token is empty (loop starts at index 1),
     * so it must complete without accessing the uninitialized options field or throwing.
     */
    @Test(timeout = 4000)
    public void test_burstToken_withEmptyToken_doesNotThrow() throws Throwable {
        PosixParser posixParser = new PosixParser();
        posixParser.burstToken("", false);
    }
}
