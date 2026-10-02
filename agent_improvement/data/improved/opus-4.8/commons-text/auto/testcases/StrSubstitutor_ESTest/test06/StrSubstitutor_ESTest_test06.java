package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.shaded.org.mockito.Mockito.mock;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test06 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * The full constructor requires a non-null variable suffix matcher. Passing
     * {@code null} for that argument must be rejected with an
     * IllegalArgumentException raised by Apache Commons Lang's Validate helper.
     */
    @Test(timeout = 4000)
    public void constructorRejectsNullSuffixMatcher() throws Throwable {
        StrLookup<String> variableResolver =
                (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher prefixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        StrMatcher nullSuffixMatcher = null;
        StrMatcher nullValueDelimiterMatcher = null;

        try {
            new StrSubstitutor(variableResolver, prefixMatcher, nullSuffixMatcher,
                    'x', nullValueDelimiterMatcher);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.isTrue fails with "Variable suffix matcher must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
