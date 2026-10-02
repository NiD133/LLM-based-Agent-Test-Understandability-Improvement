package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.function.IntUnaryOperator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RandomStringGenerator_ESTest_test16 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Verifies that calling selectFrom(null) does not throw, and that the
     * DEFAULT_MAXIMUM_CODE_POINT constant equals Character.MAX_CODE_POINT (1114111).
     */
    @Test(timeout = 4000)
    public void test_selectFromNullChars_doesNotThrow_andDefaultMaxCodePointIsUnicodeMax() throws Throwable {
        RandomStringGenerator.Builder builder = RandomStringGenerator.builder();

        // Passing null is explicitly allowed; it clears the character set without throwing
        RandomStringGenerator.Builder builderAfterSelectFrom = builder.selectFrom((char[]) null);

        // The default upper bound must cover the full Unicode range
        assertEquals(1114111, RandomStringGenerator.Builder.DEFAULT_MAXIMUM_CODE_POINT);
    }
}
