package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test12 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Character atSign = Character.valueOf('@');
        Character[] repeatedAtSigns = new Character[7];
        repeatedAtSigns[0] = atSign;
        repeatedAtSigns[1] = atSign;
        repeatedAtSigns[2] = atSign;
        repeatedAtSigns[3] = atSign;
        repeatedAtSigns[4] = atSign;
        repeatedAtSigns[5] = atSign;
        repeatedAtSigns[6] = atSign;

        AlphabetConverter converter = AlphabetConverter.createConverterFromChars(
                repeatedAtSigns, repeatedAtSigns, repeatedAtSigns);

        converter.decode((String) null);
        assertEquals(1, converter.getEncodedCharLength());
    }
}
