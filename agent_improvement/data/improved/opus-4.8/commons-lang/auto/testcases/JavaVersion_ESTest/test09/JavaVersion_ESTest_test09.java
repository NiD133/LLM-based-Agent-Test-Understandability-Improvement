package org.apache.commons.lang3;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test09 extends JavaVersion_ESTest_scaffolding {

    /**
     * "1U" is not a recognized version constant, so {@link JavaVersion#get(String)}
     * falls through to parsing it as a float. The non-numeric "U" makes that parse
     * fail, surfacing as an (undeclared) NumberFormatException.
     */
    @Test(timeout = 4000)
    public void getRejectsNonNumericVersionString() throws Throwable {
        try {
            JavaVersion.get("1U");
            fail("Expected a NumberFormatException for the unparsable version string \"1U\"");
        } catch (NumberFormatException expected) {
            // expected: "1U" cannot be parsed as a Java version number
        }
    }
}
