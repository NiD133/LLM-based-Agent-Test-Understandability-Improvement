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
public class StrSubstitutor_ESTest_test26 extends StrSubstitutor_ESTest_scaffolding {

    // A long string used as both the variable prefix and suffix delimiter.
    private static final String CUSTOM_DELIMITER = "org.apache.commons.text.lookup.ConstantStringLookup";

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        // Build a substitutor with an empty value map and a custom prefix/suffix.
        Map<String, String> emptyValueMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyValueMap, CUSTOM_DELIMITER, CUSTOM_DELIMITER);

        // Replacing null should be a no-op and not throw.
        substitutor.replace((String) null);

        // The escape character should remain the default '$', regardless of the custom prefix/suffix.
        assertEquals('$', substitutor.getEscapeChar());
    }
}
