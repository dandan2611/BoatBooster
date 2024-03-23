package fr.codinbox.boatbooster;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.codinbox.boatbooster.command.BoosterCommand;
import fr.codinbox.boatbooster.listener.VehicleListener;
import fr.codinbox.boatbooster.pojo.Config;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;

public final class BoatBooster extends JavaPlugin {
    private static BoatBooster INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper()
            .setDefaultPrettyPrinter(new DefaultPrettyPrinter());

    private File configFile;
    private Config config;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        // Load configuration
        this.configFile = new File(super.getDataFolder(), "config.json");
        if (!this.configFile.exists()) {
            this.configFile.getParentFile().mkdirs();
            this.saveBoosterConfig();
        }
        try {
            this.reloadConfig();
        } catch (Exception e) {
            super.getLogger().log(Level.SEVERE, "Unable to load booster configuration!", e);
        }

        // Register listeners
        super.getServer().getPluginManager().registerEvents(new VehicleListener(), this);

        // Register commands
        final var boosterCommand = super.getCommand("booster");
        if (boosterCommand != null)
            boosterCommand.setExecutor(new BoosterCommand());
    }

    public static @NotNull BoatBooster getInstance() {
        return Objects.requireNonNull(INSTANCE);
    }

    public @Nullable Config getBoosterConfig() {
        return this.config;
    }

    public void saveBoosterConfig() {
        if (this.config == null) {
            this.config = new Config();
            this.config.setBumpers(Collections.emptyMap());
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(this.configFile, this.config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void reloadConfig() {
        try {
            this.config = mapper.readValue(this.configFile, Config.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
