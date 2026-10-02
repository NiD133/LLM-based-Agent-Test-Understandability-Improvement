package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test13 extends Options_ESTest_scaffolding {

    /**
     * A required option registered via {@link Options#addRequiredOption} should be
     * retrievable by its name and retain its "required" flag (and lack of argument),
     * even after re-adding that same Option to the collection.
     */
    @Test(timeout = 4000)
    public void requiredOptionRetainsRequiredFlagWhenRetrievedAndReAdded() throws Throwable {
        Options options = new Options();

        // Register a required option with short name "j", long name "j",
        // no argument, and description "j". addRequiredOption returns the same
        // Options instance, so we keep operating on `options`.
        options.addRequiredOption("j", "j", false, "j");
        Option requiredOption = options.getOption("j");

        // Re-add the retrieved option back to the same Options instance.
        options.addOption(requiredOption);

        assertTrue("Option should be marked required", requiredOption.isRequired());
        assertFalse("Option should not expect an argument", requiredOption.hasArg());
    }
}
