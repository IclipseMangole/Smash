package de.IM.Smash.Functions;

import de.IM.Smash.Smash;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import java.util.ArrayList;

public class DoubleJump implements Listener {

    private final Smash smash;
    private ArrayList<Player> cooldown = new ArrayList<>();

    public DoubleJump(Smash smash) {
        this.smash = smash;
    }

    @EventHandler
    public void onJump (PlayerToggleFlightEvent event){
        Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.SURVIVAL)){
            if (player.getAllowFlight()){
                player.setAllowFlight(false);
                player.setFlying(false);
                player.setVelocity(player.getLocation().getDirection().setY(1));
                player.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 1.0F, 1.0F);
                player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 100);
                player.setExp(0.0F);
            }else {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Nur ein DoubleJump pro Sprung!");
            }
        }
    }

    @EventHandler
    public void onLand (PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.SURVIVAL)){
            if (player.isOnGround() && !player.getAllowFlight() && !cooldown.contains(player)){
                cooldown.add(player);
                Bukkit.getScheduler().runTaskLater(smash, new Runnable() {
                    @Override
                    public void run() {
                        player.setAllowFlight(true);
                        player.setExp(1.0F);
                        cooldown.remove(player);
                    }
                }, 60);
            } else if(player.isFlying()){
                player.setFlying(false);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.setAllowFlight(true);
    }
}

