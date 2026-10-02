package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test08 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * A converter built from an empty original-to-encoded map should:
     *  - default its encoded character length to 1, and
     *  - return null (rather than throw) when asked to encode a null string.
     */
    @Test(timeout = 4000)
    public void encodeNullOnEmptyConverterReturnsNullAndKeepsDefaultLength() throws Throwable {
        Map<Integer, String> emptyOriginalToEncoded = new HashMap<Integer, String>();
        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(emptyOriginalToEncoded);

        String encodedNull = converter.encode((String) null);

        assertNull(encodedNull);
        assertEquals(1, converter.getEncodedCharLength());
    }
}
