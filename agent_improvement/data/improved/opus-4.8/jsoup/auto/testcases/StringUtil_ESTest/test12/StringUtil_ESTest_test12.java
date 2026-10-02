package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.net.URL;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test12 extends StringUtil_ESTest_scaffolding {

    /**
     * Resolving a relative URL against a {@code null} base URL must fail.
     *
     * The relative URL "?3q" starts with '?', so {@link StringUtil#resolve(URL, String)}
     * dereferences the base URL (via base.getPath()) before constructing the result.
     * With a null base this dereference throws a NullPointerException, which originates
     * from the mocked URL machinery (MockURL) under the EvoSuite runtime.
     */
    @Test(timeout = 4000)
    public void resolveWithNullBaseUrlThrowsNullPointerException() throws Throwable {
        URL nullBaseUrl = null;
        String queryOnlyRelativeUrl = "?3q";

        try {
            StringUtil.resolve(nullBaseUrl, queryOnlyRelativeUrl);
            fail("Expected a NullPointerException when the base URL is null");
        } catch (NullPointerException expected) {
            // The exception carries no message; it is raised inside MockURL.
            verifyException("org.evosuite.runtime.mock.java.net.MockURL", expected);
        }
    }
}
