package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testGetInstance_Stringarray extends AbstractLangTest {

    @Test
    void testGetInstance_Stringarray() {
        assertEquals("[]", CharSet.getInstance((String[]) null).toString());
        assertEquals("[]", CharSet.getInstance().toString());
        assertEquals("[]", CharSet.getInstance(new String[] { null }).toString());
        assertEquals("[a-e]", CharSet.getInstance("a-e").toString());
    }
}
