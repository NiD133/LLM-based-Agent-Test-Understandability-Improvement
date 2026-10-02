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
public class DefaultParser_ESTest_test14 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        Options options0 = new Options();
        DefaultParser.Builder defaultParser_Builder0 = DefaultParser.builder();
        DefaultParser defaultParser0 = defaultParser_Builder0.get();
        String[] stringArray0 = new String[6];
        stringArray0[2] = "d";
        stringArray0[3] = "d";
        CommandLine commandLine0 = defaultParser0.parse(options0, stringArray0, true);
        assertNotNull(commandLine0);
    }
}
