import java.io.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

public class RoasterFuzz {
    public static void fuzzerInitialize() {

    }

    public static void fuzzerTestOneInput(byte[] data) {
        String inString = new String(data);
        try {
            Roaster.parse(JavaClassSource.class, inString);
        } catch(org.jboss.forge.roaster.ParserException e) {
            if (!e.getMessage().contains("- is this actually code?")) {
                throw(e);
            }
        }
    }
}