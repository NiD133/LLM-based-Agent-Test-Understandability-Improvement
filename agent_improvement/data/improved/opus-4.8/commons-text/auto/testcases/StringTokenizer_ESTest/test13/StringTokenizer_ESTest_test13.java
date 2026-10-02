package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test13 extends StringTokenizer_ESTest_scaffolding {

    /**
     * The multi-character delimiter "Am]OPZN#;`mL" never occurs in the input
     * "6j*-yw_2R-]", so the input is treated as a single token. Iterating over
     * every remaining token via forEachRemaining therefore consumes exactly one
     * token, leaving the iteration position (nextIndex) at 1.
     */
    @Test(timeout = 4000)
    public void forEachRemaining_withDelimiterAbsentFromInput_consumesSingleToken() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("6j*-yw_2R-]", "Am]OPZN#;`mL");

        // A no-op consumer; the token's value is irrelevant to this test.
        Consumer<Object> tokenConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        tokenizer.forEachRemaining(tokenConsumer);

        assertEquals(1, tokenizer.nextIndex());
    }
}
