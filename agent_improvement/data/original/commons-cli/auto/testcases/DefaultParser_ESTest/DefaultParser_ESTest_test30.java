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
public class DefaultParser_ESTest_test30 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        Options options0 = new Options();
        options0.addRequiredOption("s4", "s4", false, "--s4");
        DefaultParser defaultParser0 = new DefaultParser(false);
        String[] stringArray0 = new String[6];
        stringArray0[1] = "--s4";
        CommandLine commandLine0 = defaultParser0.parse(options0, stringArray0, false);
        assertNotNull(commandLine0);
    }
}
