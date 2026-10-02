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

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        PosixParser parser = new PosixParser();
        String emptyToken = "";
        boolean continueAfterNonOption = false;

        parser.burstToken(emptyToken, continueAfterNonOption);
    }
}
