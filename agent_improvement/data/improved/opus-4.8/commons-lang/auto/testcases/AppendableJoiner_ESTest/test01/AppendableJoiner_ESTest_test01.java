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
public class AppendableJoiner_ESTest_test01 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies {@link AppendableJoiner#joinI} when the prefix and delimiter both
     * point at the (initially empty) target StringBuilder and the suffix is null.
     *
     * <p>Because the element appender is a no-op and the target starts empty, the
     * only character contribution comes from appending a {@code null} suffix, which
     * {@link StringBuilder#append(CharSequence)} renders as the literal text
     * {@code "null"}.</p>
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Target StringBuilder; starts empty (capacity only, no content).
        StringBuilder target = new StringBuilder(457);

        // No-op appender: it never writes anything for an element.
        FailableBiConsumer<Appendable, Object, IOException> noOpAppender = FailableBiConsumer.nop();

        // Two elements to join: a null and another no-op consumer instance.
        FailableBiConsumer<Appendable, StringBuilder, IOException> elementValue = FailableBiConsumer.nop();
        LinkedHashSet<Object> elements = new LinkedHashSet<Object>();
        elements.add((Object) null);
        elements.add(elementValue);

        // prefix = target (empty -> contributes nothing)
        // suffix = null  (StringBuilder.append((CharSequence) null) yields "null")
        // delimiter = target (empty -> contributes nothing)
        AppendableJoiner.joinI(target, (CharSequence) target, (CharSequence) null,
                (CharSequence) target, noOpAppender, (Iterable<Object>) elements);

        assertEquals("null", target.toString());
    }
}
