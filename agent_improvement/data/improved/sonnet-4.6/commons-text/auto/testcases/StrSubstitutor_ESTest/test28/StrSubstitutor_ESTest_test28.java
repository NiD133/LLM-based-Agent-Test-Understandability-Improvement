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
public class StrSubstitutor_ESTest_test28 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null source object using a Properties-based variable map
     * returns null, regardless of what variables are defined in the properties.
     */
    @Test(timeout = 4000)
    public void test_replaceNullObjectWithProperties_returnsNull() throws Throwable {
        Properties variableMap = new Properties();

        String result = StrSubstitutor.replace((Object) null, variableMap);

        assertNull("Replacing a null source object should return null", result);
    }
}
