package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test00 extends JavaVersion_ESTest_scaffolding {

    /**
     * A version is always "at most" itself, since atMost() is an inclusive
     * (less-than-or-equal) comparison on the underlying version value.
     */
    @Test(timeout = 4000)
    public void atMostIsTrueWhenComparedToSameVersion() throws Throwable {
        JavaVersion version = JavaVersion.JAVA_27;

        boolean isAtMostItself = version.atMost(version);

        assertTrue(isAtMostItself);
    }
}
