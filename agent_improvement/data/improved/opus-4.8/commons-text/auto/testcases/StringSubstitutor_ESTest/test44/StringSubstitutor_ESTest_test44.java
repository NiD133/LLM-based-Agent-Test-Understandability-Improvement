package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test44 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * When the value Properties argument is null, {@link StringSubstitutor#replace(Object, Properties)}
     * performs no substitution and simply returns {@code source.toString()}, which is never null.
     */
    @Test(timeout = 4000)
    public void replaceWithNullPropertiesReturnsSourceToString() throws Throwable {
        Object source = new Object();

        String result = StringSubstitutor.replace(source, (Properties) null);

        assertNotNull(result);
    }
}
