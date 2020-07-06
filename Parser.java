import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Parser {

    private Scanner scanner;
    public static final Pattern LINE_STARTS_WITH_HASH = Pattern.compile("^#.*");
    public static final Pattern LINE_STARTS_WITH_NUMBER = Pattern.compile("^\\d.*");
    private String config = "";
    public Parser(String configFile) throws FileNotFoundException, IOException {
        File file = new File(configFile);
        String rawConfig = new String(Files.readAllBytes(Paths.get(configFile)));
        config = rawConfig.replaceAll("(?m)^#.*", "");
//        config = config.replaceAll("(?m)\n", "");
        config.
        scanner = new Scanner(file);

    }

    public int[] readGlobalVariables(){
        int []vars = new int[6];
        //Find first line without comments
        return null;
    }
}
