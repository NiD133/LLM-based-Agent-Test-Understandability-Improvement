package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test03 extends Options_ESTest_scaffolding {

    /**
     * Adding a required option that defines a long name should register that
     * long name, so {@link Options#hasLongOption(String)} reports it as present.
     */
    @Test(timeout = 4000)
    public void addRequiredOption_registersLongOption() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("j", "j", false, "g");

        boolean hasLongOption = options.hasLongOption("j");

        assertTrue("The long option 'j' should be registered after addRequiredOption", hasLongOption);
    }
}
