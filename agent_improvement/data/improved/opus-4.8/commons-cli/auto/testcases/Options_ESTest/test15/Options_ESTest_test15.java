package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test15 extends Options_ESTest_scaffolding {

    /**
     * Verifies that toString() on a freshly constructed (empty) Options
     * reports empty maps for both the short and long option collections.
     */
    @Test(timeout = 4000)
    public void toStringOfEmptyOptionsShowsEmptyShortAndLongMaps() throws Throwable {
        Options emptyOptions = new Options();

        String description = emptyOptions.toString();

        assertEquals("[ Options: [ short {} ] [ long {} ]", description);
    }
}
