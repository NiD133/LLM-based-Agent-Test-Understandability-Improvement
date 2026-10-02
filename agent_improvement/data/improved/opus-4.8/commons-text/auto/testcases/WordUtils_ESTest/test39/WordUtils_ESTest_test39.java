package org.apache.commons.text;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test39 extends WordUtils_ESTest_scaffolding {

    /**
     * {@link WordUtils#initials(String)} should return {@code null}
     * when given a {@code null} input String.
     */
    @Test(timeout = 4000)
    public void initialsOfNullStringReturnsNull() throws Throwable {
        String initials = WordUtils.initials((String) null);

        assertNull(initials);
    }
}
