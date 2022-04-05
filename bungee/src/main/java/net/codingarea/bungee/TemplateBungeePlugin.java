package net.codingarea.bungee;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.SneakyThrows;
import net.codingarea.bungee.listeners.PostLoginListener;
import net.codingarea.common.config.Config;
import net.codingarea.common.modules.CommonModule;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TemplateBungeePlugin extends Plugin {

    @Getter
    private Configuration config;

    @SneakyThrows
    @Override
    public void onEnable() {
        // ---

        this.saveDefaultConfig();
        this.reloadConfig();

        // ---

        TemplateBungeePlugin instance = this;

        Configuration loginConfig = this.getConfig().getSection("mongodb");

        String loginFile = loginConfig.getString("file");
        if (loginFile != null && !loginFile.isEmpty()) {
            loginConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(loginFile));
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
            public TemplateBungeePlugin providesPluginInstance() {
                return instance;
            }
        });

        // ---

        this.getProxy().getPluginManager().registerListener(this, injector.getInstance(PostLoginListener.class));
    }

    private void reloadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, new File(this.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDefaultConfig() {
        if (!this.getDataFolder().exists())
            this.getDataFolder().mkdir();

        File file = new File(this.getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = this.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
