package de.IM.Smash.Functions.SmashItems.GravityBomb;

import de.IM.Smash.Functions.SmashItems.SmashItem;
import de.IM.Smash.Smash;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BombItem extends SmashItem {

    public BombItem(){
        this.setType(Material.FIREWORK_STAR);
        this.setAmount(1);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName(ChatColor.GRAY + "Gravitations-Bombe");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Heble die Gesetze der Physik aus");
        meta.setLore(lore);
        this.setItemMeta(meta);
    }

    public void explosionTimer(Item bomb){
        new BukkitRunnable(){
            @Override
            public void run() {
                World world = bomb.getWorld();
                Location location = bomb.getLocation();
                bomb.remove();
                gravityExplosion(bomb);
            }
        }.runTaskLater(Smash.getSmash(), 100);
    }

    private void gravityExplosion(Item bomb){
        new BukkitRunnable() {

            int timer = 0;
            List<Block> blocks = generateSphere(bomb.getLocation(), 5, false);
            List<FallingBlock> fallingBlocks = new ArrayList<>();
            Random random = new Random();

            @Override
            public void run() {
                if (timer >= 100) {
                    cancel();
                    for(FallingBlock fallingBlock : fallingBlocks){
                        fallingBlock.setGravity(true);
                    }
                    bomb.getWorld().createExplosion(bomb.getLocation(), 5);
                    return;
                }
                if(timer == 0){
                    for (Block falling : blocks){
                        FallingBlock fallingBlock = falling.getWorld().spawnFallingBlock(falling.getLocation(), falling.getBlockData());
                        bomb.getWorld().getBlockAt(falling.getLocation()).setType(Material.AIR);
                        fallingBlock.setGravity(true);
                        fallingBlock.setHurtEntities(true);
                        fallingBlock.setDropItem(false);
                        fallingBlock.setVelocity(fallingBlock.getLocation().toVector().multiply(-1).add(bomb.getLocation().toVector()).multiply(0.1));
                        fallingBlocks.add(fallingBlock);
                    }
                    bomb.getWorld().playSound(bomb.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1.0F, 1.0F);
                }else {
                    for (FallingBlock fallingBlock1 : fallingBlocks) {
                        Location nextBomb = bomb.getLocation().add(random.nextInt(4) - random.nextInt(4), random.nextInt(2) - random.nextInt(2), random.nextInt(4) - random.nextInt(4));
                        fallingBlock1.setVelocity(fallingBlock1.getLocation().toVector().multiply(-1).add(nextBomb.toVector()).multiply(0.2));
                    }
                    for(Player player : Bukkit.getOnlinePlayers()){
                        double distance = player.getLocation().distance(bomb.getLocation());
                        if(distance < 10){
                            Location nextPlayer = bomb.getLocation().add(random.nextInt(2) - random.nextInt(2), random.nextInt(2) - random.nextInt(2), random.nextInt(2) - random.nextInt(2));
                            player.setVelocity(nextPlayer.subtract(player.getLocation()).toVector().multiply(Math.min(1/distance, 0.7)));
                        }
                    }
                }
                timer++;
            }
        }.runTaskTimer(Smash.getSmash(), 0, 0);
    }

    private List<Block> generateSphere(Location centerBlock, int radius, boolean hollow) {
        if (centerBlock == null) {
            return new ArrayList<>();
        }

        List<Block> circleBlocks = new ArrayList<>();

        int bx = centerBlock.getBlockX();
        int by = centerBlock.getBlockY();
        int bz = centerBlock.getBlockZ();

        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {

                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));

                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {

                        Block block = centerBlock.getWorld().getBlockAt(x, y, z);

                        circleBlocks.add(block);

                    }

                }
            }
        }

        return circleBlocks;
    }
}
