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
public class Options_ESTest_test00 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Options options = new Options();
        // Add a required option whose short-name key is null; null is a valid map key in Java
        options.addRequiredOption((String) null, (String) null, true, "Y6.E))P%{qV#g");
        // hasShortOption(null) should find the null-keyed entry and return true
        boolean hasNullShortOption = options.hasShortOption((String) null);
        assertTrue(hasNullShortOption);
    }
}
