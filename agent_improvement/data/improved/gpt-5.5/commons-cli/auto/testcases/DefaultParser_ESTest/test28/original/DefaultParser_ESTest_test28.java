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
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        Options options0 = new Options();
        DefaultParser defaultParser0 = new DefaultParser();
        String[] stringArray0 = new String[14];
        stringArray0[0] = "-s-#\"J";
        CommandLine commandLine0 = defaultParser0.parse(options0, stringArray0, true);
        assertNotNull(commandLine0);
    }
}
