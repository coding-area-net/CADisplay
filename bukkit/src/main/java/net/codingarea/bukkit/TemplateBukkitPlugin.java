package net.codingarea.bukkit;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import java.io.File;
import lombok.SneakyThrows;
import net.codingarea.bukkit.listeners.PlayerJoinListener;
import net.codingarea.common.config.Config;
import net.codingarea.common.modules.CommonModule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class TemplateBukkitPlugin extends JavaPlugin {


    @SneakyThrows
    @Override
    public void onEnable() {

        // ---

        this.reloadConfig();
        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        
        // ---

        ConfigurationSection loginConfig = this.getConfig().getConfigurationSection("mongodb");

        String loginFile = loginConfig.getString("file");
        if (loginFile != null && !loginFile.isEmpty()) {
            YamlConfiguration yamlConfig = new YamlConfiguration();
            yamlConfig.load(new File(loginFile));
            loginConfig = yamlConfig;
        }

        Config config = Config.builder()
                .databaseHost(loginConfig.getString("host"))
                .databasePort(loginConfig.getInt("port"))
                .databaseUser(loginConfig.getString("username"))
                .databasePassword(loginConfig.getString("password"))
                .databaseName(this.getConfig().getString("mongodb.database"))
                .build();

        Injector injector = Guice.createInjector(new CommonModule(config), new AbstractModule() {
            @Provides
            public TemplateBukkitPlugin providesPluginInstance() {
                return TemplateBukkitPlugin.this;
            }
        });

        // ---

        this.getServer().getPluginManager().registerEvents(injector.getInstance(PlayerJoinListener.class), this);
    }

}
