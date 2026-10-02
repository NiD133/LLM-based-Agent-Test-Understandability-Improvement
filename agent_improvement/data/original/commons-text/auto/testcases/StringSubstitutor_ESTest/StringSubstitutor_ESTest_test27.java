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
public class StringSubstitutor_ESTest_test27 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        HashMap<String, HashMap<Object, Object>> hashMap0 = new HashMap<String, HashMap<Object, Object>>();
        StringSubstitutor stringSubstitutor0 = new StringSubstitutor((Map<String, HashMap<Object, Object>>) hashMap0, "rp]j", "wjSX+E%3~eaj_xSbxQ.", 'z');
        StringBuffer stringBuffer0 = new StringBuffer();
        String string0 = stringSubstitutor0.replace(stringBuffer0, 0, 0);
        assertEquals('z', stringSubstitutor0.getEscapeChar());
        assertNotNull(string0);
    }
}
