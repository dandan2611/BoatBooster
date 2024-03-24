package fr.codinbox.boatbooster.utils;

import fr.codinbox.boatbooster.BoatBooster;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class EntityUtils {

    public static void bump(@NotNull Entity vehicle, double power, @NotNull Vector velocity) {
        velocity.setY(power);

        try {
            var vehicleHandle = ((CraftEntity) vehicle).getHandle();
            new BukkitRunnable() {
                double pow = power;
                final double step = 0.06d;
                @Override
                public void run() {
                    vehicleHandle.setDeltaMovement(velocity.getX() * 0.4, pow, velocity.getZ() * 0.4);
                    vehicleHandle.resetFallDistance();
                    if (pow <= 0) {
                        super.cancel();
                        return;
                    }
                    pow -= step;
                }
            }.runTaskTimer(BoatBooster.getInstance(), 0L, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        vehicle.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, vehicle.getLocation(), 1);
        vehicle.getLocation().getWorld().playSound(vehicle.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1f, 1f);
    }


}
