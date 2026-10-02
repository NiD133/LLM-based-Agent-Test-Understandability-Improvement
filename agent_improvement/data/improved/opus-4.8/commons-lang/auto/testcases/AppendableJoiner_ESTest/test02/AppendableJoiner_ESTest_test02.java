package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test02 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner#joinI} produces an empty result when the
     * element collection is {@code null} and the prefix, suffix, and delimiter are all
     * empty character sequences. With no elements to join, only the (empty) prefix and
     * suffix are appended, so the target StringBuilder stays empty.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        StringBuilder target = new StringBuilder();
        CharSequence emptyAffix = target; // empty prefix, suffix, and delimiter
        FailableBiConsumer<Appendable, Object, IOException> doNothingAppender = FailableBiConsumer.nop();

        StringBuilder result = AppendableJoiner.joinI(
                target,
                emptyAffix,        // prefix
                emptyAffix,        // suffix
                emptyAffix,        // delimiter
                doNothingAppender,
                (Iterable<Object>) null);

        assertEquals("", result.toString());
    }
}
