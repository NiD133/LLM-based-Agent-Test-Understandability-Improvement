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
public class DefaultParser_ESTest_test09 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser();
        Options options0 = new Options();
        Option.Builder option_Builder0 = Option.builder();
        option_Builder0.longOpt("s");
        Option option0 = option_Builder0.get();
        options0.addOption(option0);
        options0.addOption("s", true, "-s");
        String[] stringArray0 = new String[7];
        stringArray0[0] = "-s";
        stringArray0[1] = "-s";
        try {
            defaultParser0.parse(options0, stringArray0, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
