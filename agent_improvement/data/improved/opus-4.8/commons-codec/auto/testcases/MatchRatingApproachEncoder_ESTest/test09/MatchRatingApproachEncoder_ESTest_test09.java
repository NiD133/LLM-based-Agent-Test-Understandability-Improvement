package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test09 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals returns false when the first name is a single character,
     * regardless of the second name. The encoder rejects single-letter inputs
     * before performing any phonetic comparison.
     */
    @Test(timeout = 4000)
    public void isEncodeEquals_withSingleCharacterFirstName_returnsFalse() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String singleCharacterName = "O";
        String otherName = ")^[N]\"*0f'jGO`";

        boolean areHomophonous = encoder.isEncodeEquals(singleCharacterName, otherName);

        assertFalse(areHomophonous);
    }
}
