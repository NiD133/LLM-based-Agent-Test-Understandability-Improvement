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
public class DefaultParser_ESTest_test15 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Options options0 = new Options();
        Options options1 = options0.addRequiredOption("c", "-c=wt9", false, "-c");
        DefaultParser defaultParser0 = new DefaultParser(false);
        String[] stringArray0 = new String[2];
        stringArray0[0] = "-c=wt9";
        try {
            defaultParser0.parse(options1, stringArray0, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -c=wt9
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
