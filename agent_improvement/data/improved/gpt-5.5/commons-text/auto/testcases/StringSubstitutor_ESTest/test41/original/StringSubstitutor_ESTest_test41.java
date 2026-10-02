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
public class StringSubstitutor_ESTest_test41 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test41() throws Throwable {
        StringSubstitutor stringSubstitutor0 = new StringSubstitutor();
        StringBuilder stringBuilder0 = new StringBuilder("}");
        char[] charArray0 = new char[5];
        charArray0[4] = '$';
        StringBuilder stringBuilder1 = stringBuilder0.append(charArray0);
        StringBuilder stringBuilder2 = stringBuilder1.append((CharSequence) "${");
        stringBuilder2.append("}");
        StringBuilder stringBuilder3 = stringBuilder2.append((Object) stringBuilder0);
        boolean boolean0 = stringSubstitutor0.replaceIn(stringBuilder3);
        assertEquals("}\u0000\u0000\u0000\u0000${}}\u0000\u0000\u0000\u0000${}", stringBuilder3.toString());
        assertTrue(boolean0);
    }
}
