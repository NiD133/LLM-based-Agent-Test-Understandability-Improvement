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

    /**
     * Verifies that a required, no-argument option retains its properties
     * (isRequired and !hasArg) even after being re-added to the same Options instance.
     *
     * addRequiredOption returns the same Options instance (fluent API), so
     * options and optionsAfterAdd point to the same object.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Options options = new Options();

        // Add a required option with short name "j", long name "j", no argument required
        Options optionsAfterAdd = options.addRequiredOption("j", "j", false, "j");

        // Retrieve the option that was just registered
        Option requiredNoArgOption = optionsAfterAdd.getOption("j");

        // Re-add the same option instance to the original Options object (a no-op re-registration)
        options.addOption(requiredNoArgOption);

        // The option must still be marked as required and must not accept arguments
        assertTrue(requiredNoArgOption.isRequired());
        assertFalse(requiredNoArgOption.hasArg());
    }
}
