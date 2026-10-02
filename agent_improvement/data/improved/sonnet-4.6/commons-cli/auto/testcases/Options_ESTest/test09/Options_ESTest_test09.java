package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Options_ESTest_test09 extends Options_ESTest_scaffolding {

    /**
     * Verifies that merging an Options instance into another that already contains
     * the same key throws IllegalArgumentException with "Duplicate key: null".
     *
     * addOption(null, null) registers an option whose key is null. Since addOption
     * returns the same Options instance (this), optionsWithNullKey == baseOptions.
     * Calling addOptions on itself means every key is already present, so the
     * duplicate-key guard fires immediately.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Options baseOptions = new Options();
        // addOption(String opt, String description) returns 'this', so
        // optionsWithNullKey is the same object as baseOptions, now holding a null key.
        Options optionsWithNullKey = baseOptions.addOption((String) null, (String) null);

        try {
            // Merging baseOptions into itself: the null key already exists, so
            // addOptions must reject it with IllegalArgumentException.
            baseOptions.addOptions(optionsWithNullKey);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: "Duplicate key: null"
            verifyException("org.apache.commons.cli.Options", e);
        }
    }
}
