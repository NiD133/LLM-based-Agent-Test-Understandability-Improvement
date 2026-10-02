package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test28 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * A null source text always yields a null result, regardless of the
     * supplied properties (here an empty Properties instance).
     */
    @Test(timeout = 4000)
    public void replaceWithNullSourceReturnsNull() throws Throwable {
        Properties emptyProperties = new Properties();

        String result = StrSubstitutor.replace((Object) null, emptyProperties);

        assertNull(result);
    }
}
