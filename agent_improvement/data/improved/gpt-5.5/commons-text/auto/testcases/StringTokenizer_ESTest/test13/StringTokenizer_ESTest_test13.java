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

    private static final String INPUT = "6j*-yw_2R-]";
    private static final String DELIMITERS = "Am]OPZN#;`mL";

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer(INPUT, DELIMITERS);
        Consumer<Object> tokenConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        tokenizer.forEachRemaining(tokenConsumer);

        assertEquals(1, tokenizer.nextIndex());
    }
}
