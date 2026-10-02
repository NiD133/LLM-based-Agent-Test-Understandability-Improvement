package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test13 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        StringTokenizer stringTokenizer0 = new StringTokenizer("6j*-yw_2R-]", "Am]OPZN#;`mL");
        Consumer<Object> consumer0 = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        stringTokenizer0.forEachRemaining(consumer0);
        assertEquals(1, stringTokenizer0.nextIndex());
    }
}
