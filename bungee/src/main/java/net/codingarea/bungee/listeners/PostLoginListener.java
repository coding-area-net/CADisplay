package net.codingarea.bungee.listeners;

import com.google.inject.Inject;
import net.codingarea.bungee.TemplateBungeePlugin;
import net.codingarea.common.models.UserModel;
import net.codingarea.common.repositories.UserRepository;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    private final TemplateBungeePlugin plugin;
    private final UserRepository userRepository;

    @Inject
    public PostLoginListener(TemplateBungeePlugin plugin, UserRepository userRepository) {
        this.plugin = plugin;
        this.userRepository = userRepository;
    }

    @EventHandler
    public void onEvent(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        this.plugin.getProxy().getScheduler().runAsync(this.plugin, () -> {
            UserModel user = this.userRepository.findByUuid(player.getUniqueId());

            if (user == null) {
                user = UserModel.builder()
                        .uuid(player.getUniqueId())
                        .latestName(player.getName())
                        .build();
            }

            if (!user.getLatestName().equals(player.getName())) {
                user.setLatestName(player.getName());

                UserModel oldUser = this.userRepository.findByLatestName(user.getLatestName());

                if (oldUser != null) {
                    oldUser.setLatestName("#" + oldUser.getId().toString().substring(0, Math.min(oldUser.getId().toString().length(), 15)));
                    this.userRepository.save(oldUser);
                }
            }

            this.userRepository.save(user);
        });
    }
}
