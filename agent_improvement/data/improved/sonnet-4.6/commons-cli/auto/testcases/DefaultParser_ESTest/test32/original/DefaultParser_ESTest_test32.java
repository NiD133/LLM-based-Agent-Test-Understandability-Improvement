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
public class DefaultParser_ESTest_test32 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        Options options0 = new Options();
        options0.addRequiredOption("js4", "-=};SP'", true, "js4");
        DefaultParser defaultParser0 = new DefaultParser(true);
        String[] stringArray0 = new String[32];
        try {
            defaultParser0.parse(options0, stringArray0, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing required option: js4
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
