package com.cloudflare.hellourld;

import com.cloudflare.hellourld.utils.Base62EncodeDecode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class UtilsTests {

    @Test
    public void testConvertBase10toBase62_98(){
        Long id = 98L;
        String shortStr = Base62EncodeDecode.convertIdtoShortUrl(id);
        Assert.isTrue(shortStr.equals("bK"), "conversion successful");
    }

    @Test
    public void testConvertBase10toBase62_1(){
        Long id = 1L;
        String shortStr = Base62EncodeDecode.convertIdtoShortUrl(id);
        Assert.isTrue(shortStr.equals("b"), "conversion successful");
    }

    @Test
    public void testConvertBase62toBase10_98(){
        String str = "bK";
        Long id = Base62EncodeDecode.convertShortUrltoId(str);
        Assert.isTrue(id == 98L, "conversion successful");
    }

    @Test
    public void testConvertBase62toBase10_1(){
        String str = "b";
        Long id = Base62EncodeDecode.convertShortUrltoId(str);
        Assert.isTrue(id == 1L, "conversion successful");
    }
}
