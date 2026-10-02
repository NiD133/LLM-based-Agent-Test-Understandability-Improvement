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
public class AppendableJoiner_ESTest_test08 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        AppendableJoiner.Builder<StringBuilder> appendableJoiner_Builder0 = new AppendableJoiner.Builder<StringBuilder>();
        AppendableJoiner<StringBuilder> appendableJoiner0 = appendableJoiner_Builder0.get();
        StringBuilder stringBuilder0 = new StringBuilder();
        HashSet<StringBuilder> hashSet0 = new HashSet<StringBuilder>();
        StringBuilder stringBuilder1 = appendableJoiner0.join(stringBuilder0, (Iterable<StringBuilder>) hashSet0);
        assertEquals("", stringBuilder1.toString());
    }
}
