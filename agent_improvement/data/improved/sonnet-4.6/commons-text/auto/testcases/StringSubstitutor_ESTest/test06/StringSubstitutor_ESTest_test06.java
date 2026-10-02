package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test06 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06_setVariableSuffixMatcher_throwsOnNull() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // Setting a null variable suffix matcher should throw IllegalArgumentException
        try {
            substitutor.setVariableSuffixMatcher((StringMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
