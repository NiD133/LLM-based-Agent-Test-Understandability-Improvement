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
public class DefaultParser_ESTest_test19 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        String[] stringArray0 = new String[2];
        Properties properties0 = new Properties();
        properties0.put("OYj", "OYj");
        DefaultParser defaultParser0 = new DefaultParser();
        Options options0 = new Options();
        try {
            defaultParser0.parse(options0, stringArray0, properties0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Default option wasn't defined
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
