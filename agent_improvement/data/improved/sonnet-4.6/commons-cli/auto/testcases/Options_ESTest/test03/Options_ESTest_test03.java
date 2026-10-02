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
public class Options_ESTest_test03 extends Options_ESTest_scaffolding {

    /**
     * Verifies that addRequiredOption registers the long option name so that
     * hasLongOption returns true for that name.
     *
     * When both the short opt and the long opt are set to "j", the option is
     * stored in the long-options map under "j", so hasLongOption("j") must be true.
     */
    @Test(timeout = 4000)
    public void test_hasLongOption_returnsTrueAfterAddRequiredOption() throws Throwable {
        // Arrange
        Options options = new Options();
        String shortName = "j";
        String longName  = "j";
        boolean requiresArgument = false;
        String description = "g";

        options.addRequiredOption(shortName, longName, requiresArgument, description);

        // Act
        boolean hasLongOption = options.hasLongOption(longName);

        // Assert
        assertTrue(hasLongOption);
    }
}
