package me.kix.ptjson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.kix.ptjson.alt.Alt;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Converts a plaintext "alts" list to json.
 *
 * <p>
 * Searches for a "alts" plaintext file and then converts said file to "alts.json" using proper pretty print formatting.
 * All of us know the real use case for this.
 * Just don't say it aloud.
 * </p>
 *
 * @author Kix
 * Created in 06 2019.
 */
public final class PT2JSON {

    public static void main(String[] args) throws IOException {
        /* The user's home directory. On *nix this is ~. */
        final String homeDir = System.getProperty("user.home");
        /* The plain text alts file. */
        final Path plaintextAlts = new File(homeDir, "alts").toPath();

        /* Make sure we have alts to work with. */
        if (Files.exists(plaintextAlts)) {
            final Set<Alt> alts = new HashSet<>();
            Files.lines(plaintextAlts)
                    .forEachOrdered(line -> {
                        String[] parameters = line.split(":");
                        alts.add(new Alt(parameters[0], parameters[1]));
                    });

            /* The JSON alts file. */
            final Path jsonAlts = new File(homeDir, "alts.json").toPath();

            /* Make sure the file doesn't exist. */
            if (Files.exists(jsonAlts)) {
                Files.delete(jsonAlts);
            }
            Files.createFile(jsonAlts);

            /* Our "pretty-printed" gson instance. */
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();

            /* The writer for our files. */
            final PrintWriter writer = new PrintWriter(jsonAlts.toFile());

            /* The "object" class for our JSON file. */
            final JsonObject jsonObject = new JsonObject();

            /* Add our alts to the JSON object. */
            alts.forEach(alt -> jsonObject.addProperty(alt.getUsername(), alt.getPassword()));

            /* Write to our file. */
            writer.print(gson.toJson(jsonObject));

            /* Close the writing instance. */
            writer.close();
        } else {
            Logger.getGlobal().info("Plaintext Alts not found.");
        }
    }
}
