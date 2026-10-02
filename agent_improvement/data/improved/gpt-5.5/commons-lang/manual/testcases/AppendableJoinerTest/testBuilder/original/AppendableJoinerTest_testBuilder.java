package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;
import org.apache.commons.lang3.AppendableJoiner.Builder;
import org.apache.commons.lang3.text.StrBuilder;
import org.apache.commons.text.TextStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AppendableJoinerTest_testBuilder extends AbstractLangTest {

    @Test
    void testBuilder() {
        assertNotSame(AppendableJoiner.builder(), AppendableJoiner.builder());
    }
}
