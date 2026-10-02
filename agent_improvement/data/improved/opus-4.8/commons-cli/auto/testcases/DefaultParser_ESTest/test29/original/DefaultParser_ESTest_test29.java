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
public class DefaultParser_ESTest_test29 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser();
        Options options0 = new Options();
        Options options1 = options0.addOption("s", true, "-s");
        String[] stringArray0 = new String[5];
        defaultParser0.parse(options1, stringArray0, true);
        defaultParser0.handleConcatenatedOptions("-s");
    }
}
