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
public class AppendableJoiner_ESTest_test12 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        StringBuilder[] stringBuilderArray0 = new StringBuilder[0];
        AppendableJoiner.Builder<Appendable> appendableJoiner_Builder0 = AppendableJoiner.builder();
        AppendableJoiner<Appendable> appendableJoiner0 = appendableJoiner_Builder0.get();
        // Undeclared exception!
        try {
            appendableJoiner0.joinA((StringBuilder) null, (Appendable[]) stringBuilderArray0);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.apache.commons.lang3.AppendableJoiner", e);
        }
    }
}
