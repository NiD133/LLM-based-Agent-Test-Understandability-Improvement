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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        StringBuilder outputAndBoundaryText = new StringBuilder(457);
        FailableBiConsumer<Appendable, StringBuilder, IOException> ignoredStringBuilderAppender = FailableBiConsumer.nop();
        FailableBiConsumer<Appendable, Object, IOException> ignoredElementAppender = FailableBiConsumer.nop();

        LinkedHashSet<Object> elementsInIterationOrder = new LinkedHashSet<Object>();
        elementsInIterationOrder.add((Object) null);
        elementsInIterationOrder.add(ignoredStringBuilderAppender);

        AppendableJoiner.joinI(outputAndBoundaryText, (CharSequence) outputAndBoundaryText, (CharSequence) null,
                (CharSequence) outputAndBoundaryText, ignoredElementAppender, (Iterable<Object>) elementsInIterationOrder);

        // Prefix and delimiter are the initially empty builder; the null suffix appends the literal "null".
        assertEquals("null", outputAndBoundaryText.toString());
    }
}
