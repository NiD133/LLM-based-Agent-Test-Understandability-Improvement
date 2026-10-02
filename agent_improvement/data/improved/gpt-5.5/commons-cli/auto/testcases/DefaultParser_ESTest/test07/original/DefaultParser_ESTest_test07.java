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
public class DefaultParser_ESTest_test07 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser();
        Options options0 = new Options();
        options0.addOption("s", true, "-=9Udvb/'--");
        String[] stringArray0 = new String[3];
        stringArray0[0] = "-s";
        stringArray0[1] = "-=9Udvb/'--";
        CommandLine commandLine0 = defaultParser0.parse(options0, stringArray0, true);
        assertNotNull(commandLine0);
    }
}
