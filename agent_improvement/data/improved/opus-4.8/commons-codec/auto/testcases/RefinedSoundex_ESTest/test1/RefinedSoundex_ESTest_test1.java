package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test1 extends RefinedSoundex_ESTest_scaffolding {

    /**
     * When the first input string is null, {@link RefinedSoundex#difference}
     * should report no similarity (a difference score of 0), regardless of the
     * second string, rather than throwing an exception.
     */
    @Test(timeout = 4000)
    public void differenceWithNullFirstStringReturnsZero() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();

        int similarityScore = refinedSoundex.difference(null, "ov9Sw<^R");

        assertEquals(0, similarityScore);
    }
}
