package ru.euj3ne.e3firstjoinrandomspawn;

import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class E3FirstJoinRandomSpawn extends JavaPlugin implements Listener {
   private final Random random = new Random();
   private String worldName;
   private int radius;

   public void onEnable() {
      this.saveDefaultConfig();
      this.worldName = this.getConfig().getString("world", "world");
      this.radius = Math.max(1, this.getConfig().getInt("radius", 10000));
      getLogger().info("Plugin has been enabled!");
      getLogger().info("Plugin developed by: @euj3ne");
      getLogger().info("Website: " + this.getPluginMeta().getWebsite());
      Bukkit.getPluginManager().registerEvents(this, this);
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (!player.hasPlayedBefore()) {
         World world = Bukkit.getWorld(this.worldName);
         if (world == null) {
            this.getLogger().warning("World '" + this.worldName + "' is not loaded; cannot teleport " + player.getName());
            return;
         }

         Location spawn = this.getRandomLocation(world, this.radius);
         player.teleport(spawn);
      }

   }

   private Location getRandomLocation(World world, int radius) {
      int x = this.random.nextInt(radius * 2) - radius;
      int z = this.random.nextInt(radius * 2) - radius;
      int y = world.getHighestBlockYAt(x, z);
      return new Location(world, (double)x + 0.5D, (double)(y + 1), (double)z + 0.5D);
   }

   public void onDisable() {
      this.getLogger().info("Plugin has been disabled!");
   }
}
    
