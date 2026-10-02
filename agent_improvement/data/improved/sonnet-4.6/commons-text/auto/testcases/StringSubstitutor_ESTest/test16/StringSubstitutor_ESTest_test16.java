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
public class StringSubstitutor_ESTest_test16 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_replaceIn_nullStringBuilder_returnsFalseAndEscapeCharRemainsDefault() throws Throwable {
        // createInterpolator() builds a substitutor whose escape character is '$'
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // replaceIn with a null source must return false (no replacement possible)
        // offset and length are both 36 (the ASCII value of '$'), but are irrelevant
        // because the null check short-circuits before they are used
        int offset = (int) '$';
        int length = (int) '$';
        boolean replaced = interpolator.replaceIn((StringBuilder) null, offset, length);

        assertFalse("replaceIn should return false when the source StringBuilder is null", replaced);
        assertEquals("Escape character should be '$' for the interpolator", '$', interpolator.getEscapeChar());
    }
}
