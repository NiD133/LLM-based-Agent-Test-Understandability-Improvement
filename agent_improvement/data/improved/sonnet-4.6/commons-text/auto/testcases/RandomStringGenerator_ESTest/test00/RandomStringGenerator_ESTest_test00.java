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
public class RandomStringGenerator_ESTest_test00 extends RandomStringGenerator_ESTest_scaffolding {

    /**
     * Tests that using {@link IntUnaryOperator#identity()} as the RNG causes an
     * {@link IndexOutOfBoundsException} when generating a string from a restricted
     * character pool.
     *
     * When {@code selectFrom} is called with two null characters ('\0', '\0'), the
     * builder deduplicates them into a character set of size 1. The identity operator
     * returns its input unchanged, so {@code identity.applyAsInt(1) == 1}, which is
     * used as the list index. Since the list has only one element (index 0), accessing
     * index 1 throws {@link IndexOutOfBoundsException}.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build a generator restricted to two null chars ('\0', '\0').
        // After deduplication the effective character pool has size 1.
        char[] twoNullChars = new char[2]; // both elements are '\0' by default
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .selectFrom(twoNullChars)
                // identity(n) == n, so it returns the pool size instead of a valid index
                .usingRandom(IntUnaryOperator.identity())
                .get();

        // Requesting a large string triggers the broken index immediately:
        // identity.applyAsInt(1) == 1, but characterList.get(1) is out of bounds.
        try {
            generator.generate(1114111);
            fail("Expecting exception: IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {
        }
    }
}
