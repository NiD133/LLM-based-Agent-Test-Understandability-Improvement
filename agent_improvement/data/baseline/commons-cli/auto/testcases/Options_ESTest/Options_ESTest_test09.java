package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test09 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Options options0 = new Options();
        Options options1 = options0.addOption((String) null, (String) null);
        // Undeclared exception!
        try {
            options0.addOptions(options1);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Duplicate key: null
            //
            verifyException("org.apache.commons.cli.Options", e);
        }
    }
}
