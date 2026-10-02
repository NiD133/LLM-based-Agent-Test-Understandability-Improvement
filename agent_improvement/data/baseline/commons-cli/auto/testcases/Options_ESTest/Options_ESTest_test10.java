package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test10 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Options options0 = new Options();
        Options options1 = new Options();
        options1.addOption("v", " ]", false, "MBMwU(V1:l*[\"cE");
        Options options2 = options0.addOptions(options1);
        assertNotSame(options1, options2);
    }
}
