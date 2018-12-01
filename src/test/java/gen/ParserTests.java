package gen;

import org.junit.Test;

public class ParserTests
{
    @Test
    public void testSingleAtoZRegex()
    {
        String s = "";
        boolean result = s.matches("^([a-zA-z])");
        System.out.println(result);
    }
}
