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
public class AppendableJoiner_ESTest_test02 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02_joinI_withNullIterable_returnsEmptyString() throws Throwable {
        // Use an empty StringBuilder as both the output target and as the empty prefix/suffix/delimiter
        StringBuilder outputBuffer = new StringBuilder();
        CharSequence emptySequence = outputBuffer;
        FailableBiConsumer<Appendable, Object, IOException> noOpAppender = FailableBiConsumer.nop();

        // Joining a null iterable with empty prefix, suffix, and delimiter should produce an empty result
        StringBuilder result = AppendableJoiner.joinI(outputBuffer, emptySequence, emptySequence, emptySequence, noOpAppender, (Iterable<Object>) null);

        assertEquals("", result.toString());
    }
}
