package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test12 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that joinA throws NullPointerException when a null Appendable is
     * passed as the target, even when the elements array is empty.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // An empty array of elements — the NPE must come from the null appendable, not the elements
        StringBuilder[] noElements = new StringBuilder[0];

        // Build a default AppendableJoiner with no prefix, suffix, or delimiter configured
        AppendableJoiner<Appendable> joiner = AppendableJoiner.<Appendable>builder().get();

        // Passing null as the Appendable target should trigger NullPointerException
        // because AppendableJoiner unconditionally calls appendable.append(prefix) first
        try {
            joiner.joinA((StringBuilder) null, (Appendable[]) noElements);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.lang3.AppendableJoiner", e);
        }
    }
}
