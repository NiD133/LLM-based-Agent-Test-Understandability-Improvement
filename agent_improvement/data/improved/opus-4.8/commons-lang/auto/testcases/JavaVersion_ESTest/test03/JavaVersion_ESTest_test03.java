package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test03 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@link JavaVersion#atLeast(JavaVersion)} returns false when the
     * version is lower than the required version: Java 17 is not at least Java 24.
     */
    @Test(timeout = 4000)
    public void atLeast_whenVersionIsLowerThanRequired_returnsFalse() throws Throwable {
        JavaVersion requiredVersion = JavaVersion.JAVA_24;
        JavaVersion actualVersion = JavaVersion.JAVA_17;

        boolean meetsRequirement = actualVersion.atLeast(requiredVersion);

        assertFalse("Java 17 should not satisfy a requirement of at least Java 24", meetsRequirement);
    }
}
