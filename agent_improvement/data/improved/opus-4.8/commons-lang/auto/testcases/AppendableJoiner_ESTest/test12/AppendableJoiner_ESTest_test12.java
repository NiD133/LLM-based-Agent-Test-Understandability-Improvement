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
     * Verifies that {@link AppendableJoiner#joinA(Appendable, Object...)} throws a
     * NullPointerException when the target Appendable is {@code null}. The joiner first
     * tries to append the prefix onto the target, which fails immediately on the null target,
     * even though the elements array is empty.
     */
    @Test(timeout = 4000)
    public void joinIntoNullAppendableThrowsNullPointerException() throws Throwable {
        AppendableJoiner.Builder<Appendable> builder = AppendableJoiner.builder();
        AppendableJoiner<Appendable> joiner = builder.get();

        StringBuilder nullTarget = null;
        StringBuilder[] noElements = new StringBuilder[0];

        try {
            joiner.joinA((Appendable) nullTarget, (Appendable[]) noElements);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null target has no append() method to call; no detail message is set.
            verifyException("org.apache.commons.lang3.AppendableJoiner", e);
        }
    }
}
