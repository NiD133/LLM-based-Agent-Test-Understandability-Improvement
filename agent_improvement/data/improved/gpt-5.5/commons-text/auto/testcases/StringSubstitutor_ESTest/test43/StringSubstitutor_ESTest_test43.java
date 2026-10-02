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
public class StringSubstitutor_ESTest_test43 extends StringSubstitutor_ESTest_scaffolding {

    private static final int OFFSET = 7;
    private static final int LENGTH = 662;

    @Test(timeout = 4000)
    public void test43() throws Throwable {
        final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        final String interpolatorDescription = interpolator.toString();
        final StringLookup recursiveLookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());

        doReturn((String) null, (String) null).when(recursiveLookup).toString();
        doReturn(interpolatorDescription).when(recursiveLookup).apply(anyString());

        interpolator.setVariableResolver(recursiveLookup);

        try {
            interpolator.replace(interpolatorDescription, OFFSET, LENGTH);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
