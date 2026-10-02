package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test02 extends JavaVersion_ESTest_scaffolding {

    /**
     * A Java version is always "at least" itself, since atLeast() uses a
     * greater-than-or-equal comparison on the version value.
     */
    @Test(timeout = 4000)
    public void atLeastReturnsTrueWhenComparedToSameVersion() throws Throwable {
        JavaVersion version = JavaVersion.JAVA_11;

        boolean isAtLeastSelf = version.atLeast(version);

        assertTrue(isAtLeastSelf);
    }
}
