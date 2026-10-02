package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test08 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals returns false when one of the names is only a single
     * character long, regardless of the other name's content. Here the second
     * name "z" has length 1, so the comparison short-circuits to false before
     * any phonetic encoding is performed.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseWhenSecondNameIsSingleCharacter() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String multiCharacterName = "T7pioZh#;op/_0`\".?";
        String singleCharacterName = "z";

        boolean namesAreHomophonous =
                encoder.isEncodeEquals(multiCharacterName, singleCharacterName);

        assertFalse(namesAreHomophonous);
    }
}
