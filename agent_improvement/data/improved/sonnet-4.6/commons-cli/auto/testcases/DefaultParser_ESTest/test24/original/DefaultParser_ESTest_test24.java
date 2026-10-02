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
public class DefaultParser_ESTest_test24 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser(false);
        Options options0 = new Options();
        String[] stringArray0 = new String[9];
        stringArray0[1] = "---fo=9EbdgIv{'d^Zj";
        try {
            defaultParser0.parse(options0, stringArray0, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: ---fo=9EbdgIv{'d^Zj
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
