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
public class QCodec_ESTest_test09 extends QCodec_ESTest_scaffolding {

    /**
     * A newly constructed QCodec should not encode blank (SPACE) characters by
     * default, so {@link QCodec#isEncodeBlanks()} returns {@code false} until
     * {@code setEncodeBlanks(true)} is called.
     */
    @Test(timeout = 4000)
    public void isEncodeBlanksDefaultsToFalse() throws Throwable {
        QCodec qCodec = new QCodec(Charset.defaultCharset());

        boolean encodeBlanksEnabled = qCodec.isEncodeBlanks();

        assertFalse(encodeBlanksEnabled);
    }
}
