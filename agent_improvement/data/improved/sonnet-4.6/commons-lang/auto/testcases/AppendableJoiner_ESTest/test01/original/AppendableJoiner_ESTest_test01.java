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
        StringBuilder stringBuilder0 = new StringBuilder(457);
        FailableBiConsumer<Appendable, StringBuilder, IOException> failableBiConsumer0 = FailableBiConsumer.nop();
        FailableBiConsumer<Appendable, Object, IOException> failableBiConsumer1 = FailableBiConsumer.nop();
        LinkedHashSet<Object> linkedHashSet0 = new LinkedHashSet<Object>();
        linkedHashSet0.add((Object) null);
        linkedHashSet0.add(failableBiConsumer0);
        AppendableJoiner.joinI(stringBuilder0, (CharSequence) stringBuilder0, (CharSequence) null, (CharSequence) stringBuilder0, failableBiConsumer1, (Iterable<Object>) linkedHashSet0);
        assertEquals("null", stringBuilder0.toString());
    }
}
