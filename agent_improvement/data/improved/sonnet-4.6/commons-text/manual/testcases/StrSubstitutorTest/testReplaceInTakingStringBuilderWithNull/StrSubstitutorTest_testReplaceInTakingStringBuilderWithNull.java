package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class StrSubstitutorTest_testReplaceInTakingStringBuilderWithNull {

    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        // Verify that replaceIn returns false (not throw) when given a null StringBuilder
        final Map<String, Object> map = new HashMap<>();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(map, "", "", 'T', "K+<'f");
        assertFalse(strSubstitutor.replaceIn((StringBuilder) null));
    }
}
