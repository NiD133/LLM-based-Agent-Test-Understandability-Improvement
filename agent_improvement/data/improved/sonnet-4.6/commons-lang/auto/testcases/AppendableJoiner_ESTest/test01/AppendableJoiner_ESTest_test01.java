package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.util.LinkedHashSet;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test01 extends AppendableJoiner_ESTest_scaffolding {

    /**
     * Verifies that joinI appends the literal string "null" when the suffix argument is null,
     * because StringBuilder.append((CharSequence) null) writes "null".
     * The prefix and delimiter both point to the (initially empty) target, and the
     * no-op element appender writes nothing, so only the null suffix contributes output.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        StringBuilder target = new StringBuilder(457);

        // No-op appender typed to StringBuilder (added to elements set below)
        FailableBiConsumer<Appendable, StringBuilder, IOException> nopAppenderForSet = FailableBiConsumer.nop();
        // No-op appender used as the element renderer — writes nothing for each element
        FailableBiConsumer<Appendable, Object, IOException> nopElementAppender = FailableBiConsumer.nop();

        // Two-element ordered set: null first, then the no-op consumer instance
        LinkedHashSet<Object> elements = new LinkedHashSet<Object>();
        elements.add((Object) null);
        elements.add(nopAppenderForSet);

        // prefix = target (empty string), suffix = null, delimiter = target (empty string)
        AppendableJoiner.joinI(target, (CharSequence) target, (CharSequence) null, (CharSequence) target, nopElementAppender, (Iterable<Object>) elements);

        // The null suffix causes StringBuilder.append(null) to write the string "null"
        assertEquals("null", target.toString());
    }
}
