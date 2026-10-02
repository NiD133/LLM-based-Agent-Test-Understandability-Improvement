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
public class StringSubstitutor_ESTest_test26 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        HashMap<String, Object> hashMap0 = new HashMap<String, Object>();
        StringSubstitutor stringSubstitutor0 = new StringSubstitutor((Map<String, Object>) hashMap0);
        stringSubstitutor0.replace((StringBuffer) null, 36, 36);
        assertEquals('$', stringSubstitutor0.getEscapeChar());
    }
}
