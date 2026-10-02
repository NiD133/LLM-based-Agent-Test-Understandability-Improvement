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
public class DefaultParser_ESTest_test21 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        Options options0 = new Options();
        options0.addRequiredOption("s4", "s4", false, "--s4");
        DefaultParser defaultParser0 = new DefaultParser();
        String[] stringArray0 = new String[8];
        stringArray0[7] = "-=};SP'";
        try {
            defaultParser0.parse(options0, stringArray0, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -=};SP'
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
