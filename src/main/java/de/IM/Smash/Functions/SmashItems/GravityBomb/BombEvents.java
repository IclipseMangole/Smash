package de.IM.Smash.Functions.SmashItems.GravityBomb;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class BombEvents implements Listener {

    @EventHandler
    public void onThrow(PlayerInteractEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();
        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
            if (!itemStack.getType().equals(Material.AIR)) {
                if (itemStack.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Gravitations-Bombe")) {
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    BombItem itemstack = (BombItem) itemStack;
                    Item bomb = itemstack.spawnBombEntity(player.getEyeLocation());
                    bomb.setVelocity(player.getLocation().getDirection().multiply(1.5));
                    itemstack.explosionTimer(bomb);
                    bomb.setPickupDelay(1000);
                }
            }else {
                world.dropItem(player.getLocation(), new BombItem());
            }
        }
    }

    @EventHandler
    public void onFallingBlock(EntityChangeBlockEvent event){
        event.setCancelled(true);
    }

}
