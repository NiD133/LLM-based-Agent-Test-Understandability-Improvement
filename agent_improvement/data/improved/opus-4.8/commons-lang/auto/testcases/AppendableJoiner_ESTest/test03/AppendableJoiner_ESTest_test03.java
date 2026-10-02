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
public class AppendableJoiner_ESTest_test03 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner#joinSB} returns the very same
     * StringBuilder instance it was given as the join target, even when the
     * array of elements to join is empty.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        StringBuilder target = new StringBuilder();
        // An appender that does nothing; with no elements to join it is never invoked.
        FailableBiConsumer<Appendable, Object, IOException> noOpAppender = FailableBiConsumer.nop();
        Object[] noElements = new Object[0];

        // The empty target doubles as the (empty) prefix, suffix, and delimiter.
        StringBuilder result = AppendableJoiner.joinSB(
                target,
                (CharSequence) target,  // prefix
                (CharSequence) target,  // suffix
                (CharSequence) target,  // delimiter
                noOpAppender,
                noElements);

        // joinSB appends into and returns the same StringBuilder.
        assertSame(result, target);
    }
}
