package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test16 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that passing a {@code null} character array to {@link RandomStringGenerator.Builder#selectFrom(char...)}
     * is accepted (it reverts to the default behavior of allowing any character) and that the builder's
     * {@code DEFAULT_MAXIMUM_CODE_POINT} constant equals {@link Character#MAX_CODE_POINT} (1,114,111).
     */
    @Test(timeout = 4000)
    public void selectFromNullCharArrayIsAcceptedAndDefaultMaxCodePointIsCharacterMax() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // selectFrom(null) must not throw; it returns the same builder for chaining.
        builder.selectFrom((char[]) null);

        assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
        assertEquals(Character.MAX_CODE_POINT, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
    }
}
