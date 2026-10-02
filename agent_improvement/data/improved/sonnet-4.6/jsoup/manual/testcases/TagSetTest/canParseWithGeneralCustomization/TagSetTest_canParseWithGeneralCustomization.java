package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TagSetTest_canParseWithGeneralCustomization {

    @Test
    void canParseWithGeneralCustomization() {
        // Customize the parser so that any unknown (non-standard) tag is treated as self-closing.
        // Known tags like <script> keep their default behaviour regardless of how they appear in the source.
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag())
                tag.set(Tag.SelfClose);
        });

        Document doc = Jsoup.parse("<custom-data />Bar <script />Text", parser);

        // <custom-data> is unknown → self-closing, so it wraps only itself (no content).
        // <script> is a known block tag → self-closing flag ignored, so it consumes "Text".
        assertEquals("<custom-data></custom-data>Bar\n<script>Text</script>", doc.body().html());
    }
}
