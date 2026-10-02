package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test11 extends QCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_encodeBlanksDefaultsToFalseWhenCreatedWithCharsetName() throws Throwable {
        // "l2" is the IANA alias for ISO-8859-2 (Latin-2); encodeBlanks should be false by default
        QCodec codec = new QCodec("l2");
        assertFalse(codec.isEncodeBlanks());
    }
}
