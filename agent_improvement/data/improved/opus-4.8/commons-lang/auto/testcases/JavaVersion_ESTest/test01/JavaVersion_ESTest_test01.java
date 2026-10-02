package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test01 extends JavaVersion_ESTest_scaffolding {

    /**
     * {@link JavaVersion#atMost(JavaVersion)} should report that a newer Java
     * version is NOT at most an older one. Here Java 25 is compared against
     * Java 19, so {@code atMost} must return {@code false}.
     */
    @Test(timeout = 4000)
    public void atMost_newerVersionAgainstOlderVersion_returnsFalse() throws Throwable {
        JavaVersion newerVersion = JavaVersion.JAVA_25;
        JavaVersion olderVersion = JavaVersion.get("19");

        boolean newerIsAtMostOlder = newerVersion.atMost(olderVersion);

        assertFalse(newerIsAtMostOlder);
    }
}
