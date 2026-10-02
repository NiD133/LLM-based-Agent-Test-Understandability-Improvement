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
public class Options_ESTest_test01 extends Options_ESTest_scaffolding {

    // A freshly created Options instance has no options registered, so hasShortOption should return false.
    @Test(timeout = 4000)
    public void test_hasShortOption_returnsFalse_whenNoOptionsRegistered() throws Throwable {
        Options emptyOptions = new Options();
        boolean hasShortOptionJ = emptyOptions.hasShortOption("j");
        assertFalse(hasShortOptionJ);
    }
}
