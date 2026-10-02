package org.jsoup.nodes;

import org.junit.jupiter.api.Test;

import static org.jsoup.nodes.Entities.EscapeMode.xhtml;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_xhtml {
    private static final XhtmlEntity[] XHTML_ENTITIES = {
        new XhtmlEntity("amp", 38),
        new XhtmlEntity("gt", 62),
        new XhtmlEntity("lt", 60),
        new XhtmlEntity("quot", 34)
    };

    @Test
    public void xhtml() {
        for (XhtmlEntity entity : XHTML_ENTITIES) {
            assertEquals(entity.codepoint, xhtml.codepointForName(entity.name));
        }

        for (XhtmlEntity entity : XHTML_ENTITIES) {
            assertEquals(entity.name, xhtml.nameForCodepoint(entity.codepoint));
        }
    }

    private static final class XhtmlEntity {
        private final String name;
        private final int codepoint;

        private XhtmlEntity(String name, int codepoint) {
            this.name = name;
            this.codepoint = codepoint;
        }
    }
}
