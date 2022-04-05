package net.codingarea.bukkit.listeners;

import com.google.inject.Inject;
import net.codingarea.bukkit.TemplateBukkitPlugin;
import net.codingarea.common.models.UserModel;
import net.codingarea.common.repositories.UserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final TemplateBukkitPlugin plugin;

    private final UserRepository userRepository;

    @Inject
    public PlayerJoinListener(TemplateBukkitPlugin plugin, UserRepository userRepository) {
        this.plugin = plugin;
        this.userRepository = userRepository;
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        this.plugin.getServer().getScheduler().runTaskAsynchronously(this.plugin, () -> {
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
