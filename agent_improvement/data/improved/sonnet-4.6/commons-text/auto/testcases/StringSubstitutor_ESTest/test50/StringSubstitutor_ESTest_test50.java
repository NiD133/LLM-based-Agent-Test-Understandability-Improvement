package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test50 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that wrapping an interpolator-based StringSubstitutor inside another StringSubstitutor
     * preserves the default escape character '$'.
     *
     * StringSubstitutor(StringSubstitutor) delegates its lookup to the provided substitutor,
     * and the escape character should default to '$' regardless of the delegate.
     */
    @Test(timeout = 4000)
    public void test50_escapeCharIsDefaultDollarSignWhenWrappingInterpolator() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        StringSubstitutor wrapper = new StringSubstitutor(interpolator);

        assertEquals('$', wrapper.getEscapeChar());
    }
}
