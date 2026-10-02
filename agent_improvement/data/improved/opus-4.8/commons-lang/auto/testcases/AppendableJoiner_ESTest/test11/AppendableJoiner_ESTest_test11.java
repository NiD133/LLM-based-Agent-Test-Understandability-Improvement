package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test11 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that {@link AppendableJoiner#joinSB} appends into the given
     * StringBuilder and returns that same instance.
     *
     * <p>Here the prefix, suffix and delimiter are all empty CharSequences, the
     * element appender is a no-op, and the element array contains only nulls, so
     * nothing meaningful is written. The contract still requires the original
     * target StringBuilder to be returned.</p>
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        StringBuilder target = new StringBuilder("");
        CharSequence emptyPrefixAndDelimiter = new StringBuffer("");

        // A no-op appender: each element is ignored, nothing is written.
        FailableBiConsumer<Appendable, StringBuilder, IOException> noOpAppender = FailableBiConsumer.nop();

        // Seven null elements; combined with the no-op appender they add nothing.
        StringBuilder[] elements = new StringBuilder[7];

        StringBuilder result = AppendableJoiner.joinSB(
                target,
                emptyPrefixAndDelimiter,   // prefix
                (CharSequence) target,     // suffix (the empty target itself)
                emptyPrefixAndDelimiter,   // delimiter
                noOpAppender,
                elements);

        assertSame(target, result);
    }
}
