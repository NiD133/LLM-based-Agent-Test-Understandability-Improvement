package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test06 extends JavaVersion_ESTest_scaffolding {

    /**
     * An unrecognized version string whose decimal part parses as greater than .9
     * is mapped to the catch-all {@link JavaVersion#JAVA_RECENT} constant rather
     * than returning {@code null}.
     */
    @Test(timeout = 4000)
    public void getResolvesVersionWithHighDecimalToJavaRecent() throws Throwable {
        String unrecognizedVersionWithHighDecimal = "'}wb,.9";

        JavaVersion resolved = JavaVersion.get(unrecognizedVersionWithHighDecimal);

        assertEquals(JavaVersion.JAVA_RECENT, resolved);
    }
}
