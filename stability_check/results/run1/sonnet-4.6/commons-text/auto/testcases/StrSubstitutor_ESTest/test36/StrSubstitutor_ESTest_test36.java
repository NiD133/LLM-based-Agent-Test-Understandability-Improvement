package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test36 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * When Properties is null, replace() falls back to source.toString() and returns a non-null result.
     */
    @Test(timeout = 4000)
    public void test_replaceWithNullProperties_returnsSourceToString() throws Throwable {
        Object source = new Object();
        String result = StrSubstitutor.replace(source, (Properties) null);
        assertNotNull(result);
    }
}
