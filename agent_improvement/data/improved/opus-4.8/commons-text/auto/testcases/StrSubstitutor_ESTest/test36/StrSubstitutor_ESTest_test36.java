package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test36 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * When the value properties are null, {@link StrSubstitutor#replace(Object, Properties)}
     * performs no substitution and simply returns the source object's {@code toString()}.
     * That result is always non-null for a plain (non-null) source object.
     */
    @Test(timeout = 4000)
    public void replaceWithNullPropertiesReturnsSourceToString() throws Throwable {
        Object source = new Object();

        String result = StrSubstitutor.replace(source, (Properties) null);

        assertNotNull(result);
    }
}
