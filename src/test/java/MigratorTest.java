import com.artformgames.plugin.usersuffix.migrator.ArtEssMigrator;
import org.junit.Test;

import java.util.regex.Matcher;

public class MigratorTest {

    @Test
    public void patternTest() {
        testPattern("suffix.100.&(#B2DFEE)凛冬之国的艾莎&(#B2DFEE)");
        testPattern("suffix.100.&fQwQ&(#B2DFEE)");
        testPattern("suffix.100.&fQwQ&f");
        testPattern("suffix.100.QwQ&f");
        testPattern("suffix.100.&fQwQ");
        testPattern("suffix.100.QwQ");
    }

    public void testPattern(String input) {
        Matcher matcher = ArtEssMigrator.PATTERN.matcher(input);

        while (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
                Matcher c = ArtEssMigrator.CONTENT.matcher(input);
                if (c.matches()) {
                    System.out.println("Content: " + c.group(1));
                    System.out.println("Color: " + c.group(2));
                }
            }
        }
        System.out.println("----------------");
    }

}
