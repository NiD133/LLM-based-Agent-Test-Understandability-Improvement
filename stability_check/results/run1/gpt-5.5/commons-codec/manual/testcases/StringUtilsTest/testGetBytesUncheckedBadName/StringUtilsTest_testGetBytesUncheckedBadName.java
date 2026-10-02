import static org.junit.Assert.assertThrows;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;

public class StringUtilsTest_testGetBytesUncheckedBadName {

    private static final String STRING_FIXTURE = "Hello World";
    private static final String UNKNOWN_CHARSET_NAME = "UNKNOWN";

    @Test
    public void testGetBytesUncheckedBadName() {
        assertThrows(
                IllegalStateException.class,
                () -> StringUtils.getBytesUnchecked(STRING_FIXTURE, UNKNOWN_CHARSET_NAME));
    }
}
