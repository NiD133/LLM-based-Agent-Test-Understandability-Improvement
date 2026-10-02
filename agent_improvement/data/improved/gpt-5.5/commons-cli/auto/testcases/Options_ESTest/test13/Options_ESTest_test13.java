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
public class Options_ESTest_test13 extends Options_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Options options = new Options();
        Options optionsAfterAddingRequiredOption = options.addRequiredOption("j", "j", false, "j");
        Option requiredOption = optionsAfterAddingRequiredOption.getOption("j");

        options.addOption(requiredOption);

        assertTrue(requiredOption.isRequired());
        assertFalse(requiredOption.hasArg());
    }
}
