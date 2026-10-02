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
public class Options_ESTest_test09 extends Options_ESTest_scaffolding {

    /**
     * Adding the options from one {@link Options} into another must reject any
     * duplicate key. Here both instances share the same option (an option whose
     * short name is null), so {@code addOptions} should fail with an
     * {@link IllegalArgumentException} reporting the duplicate "null" key.
     */
    @Test(timeout = 4000)
    public void addOptionsRejectsDuplicateNullKey() throws Throwable {
        Options options = new Options();
        // Both the receiver and the argument now contain the same null-keyed option,
        // because addOption mutates and returns the same instance.
        Options optionsWithNullKey = options.addOption((String) null, (String) null);

        try {
            options.addOptions(optionsWithNullKey);
            fail("Expecting exception: IllegalArgumentException for duplicate key 'null'");
        } catch (IllegalArgumentException e) {
            // Message: "Duplicate key: null"
            verifyException("org.apache.commons.cli.Options", e);
        }
    }
}
