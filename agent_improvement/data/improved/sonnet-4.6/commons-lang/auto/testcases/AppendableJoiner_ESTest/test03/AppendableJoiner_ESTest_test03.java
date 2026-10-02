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
public class AppendableJoiner_ESTest_test03 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03_joinSB_withEmptyArray_returnsSameStringBuilderInstance() throws Throwable {
        // Use the same StringBuilder as target, prefix, suffix, and delimiter
        StringBuilder target = new StringBuilder();
        FailableBiConsumer<Appendable, Object, IOException> noOpAppender = FailableBiConsumer.nop();
        Object[] emptyElements = new Object[0];

        // joinSB should append prefix+suffix (both empty here) and return the same StringBuilder
        StringBuilder result = AppendableJoiner.joinSB(target, (CharSequence) target, (CharSequence) target, (CharSequence) target, noOpAppender, emptyElements);

        assertSame(result, target);
    }
}
