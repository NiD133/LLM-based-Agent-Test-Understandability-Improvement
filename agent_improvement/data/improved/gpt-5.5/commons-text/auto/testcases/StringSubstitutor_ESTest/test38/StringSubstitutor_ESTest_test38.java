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
public class StringSubstitutor_ESTest_test38 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        final char[] nullInput = (char[]) null;
        final int startOffset = (int) '$';
        final int length = (int) '$';
        final char defaultEscapeCharacter = '$';

        final StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        interpolator.replace(nullInput, startOffset, length);

        assertEquals(defaultEscapeCharacter, interpolator.getEscapeChar());
    }
}
