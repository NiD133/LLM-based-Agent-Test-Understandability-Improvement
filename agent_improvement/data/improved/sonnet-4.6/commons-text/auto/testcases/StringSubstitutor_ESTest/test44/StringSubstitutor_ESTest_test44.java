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
public class StringSubstitutor_ESTest_test44 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing with a null Properties lookup returns a non-null result.
     * When no properties are provided, StringSubstitutor should still return a string
     * (the source object's toString() with no substitutions applied).
     */
    @Test(timeout = 4000)
    public void test44_replaceWithNullProperties_returnsNonNullString() throws Throwable {
        Object source = new Object();
        String result = StringSubstitutor.replace(source, (Properties) null);
        assertNotNull(result);
    }
}
