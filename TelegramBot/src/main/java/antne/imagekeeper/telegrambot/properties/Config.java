package antne.imagekeeper.telegrambot.properties;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class Config {

    private static Settings settings;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        try (InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("application.yml")) {
            Yaml yaml = new Yaml();
            settings = yaml.loadAs(inputStream, Settings.class);
            settings.getBot().getInfo().getLocalization().init();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static Settings getSettings() {
        return settings;
    }
}
