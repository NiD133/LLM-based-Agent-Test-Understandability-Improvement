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

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        URL ftpBaseUrl = MockURL.getFtpExample();
        URL resolvedUrl = StringUtil.resolve(ftpBaseUrl, "#~=AewMCu5k[");
        assertEquals("ftp://ftp.someFakeButWellFormedURL.org/fooExample#~=AewMCu5k[", resolvedUrl.toExternalForm());
    }
}
