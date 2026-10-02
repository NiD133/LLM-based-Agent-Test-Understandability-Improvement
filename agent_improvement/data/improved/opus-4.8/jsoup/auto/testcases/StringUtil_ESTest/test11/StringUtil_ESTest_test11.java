package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import java.net.URL;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.net.MockURL;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test11 extends StringUtil_ESTest_scaffolding {

    /**
     * Resolving a relative reference that is only a fragment ("#...") against an
     * absolute base URL should keep the base URL untouched and simply append the
     * fragment to it.
     */
    @Test(timeout = 4000)
    public void resolveFragmentReferenceAppendsFragmentToBaseUrl() throws Throwable {
        URL baseUrl = MockURL.getFtpExample();
        String fragmentReference = "#~=AewMCu5k[";

        URL resolvedUrl = StringUtil.resolve(baseUrl, fragmentReference);

        assertEquals(
            "ftp://ftp.someFakeButWellFormedURL.org/fooExample#~=AewMCu5k[",
            resolvedUrl.toExternalForm());
    }
}
