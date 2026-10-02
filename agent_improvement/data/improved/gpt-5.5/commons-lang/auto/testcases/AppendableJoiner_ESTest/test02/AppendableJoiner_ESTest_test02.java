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
    public void test02() throws Throwable {
        StringBuilder sharedEmptySequence = new StringBuilder();
        FailableBiConsumer<Appendable, Object, IOException> noOpAppender = FailableBiConsumer.nop();

        StringBuilder joinedResult = AppendableJoiner.joinI(
                sharedEmptySequence,
                (CharSequence) sharedEmptySequence,
                (CharSequence) sharedEmptySequence,
                (CharSequence) sharedEmptySequence,
                noOpAppender,
                (Iterable<Object>) null);

        assertEquals("", joinedResult.toString());
    }
}
