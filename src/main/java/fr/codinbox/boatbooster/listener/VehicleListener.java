package fr.codinbox.boatbooster.listener;

import fr.codinbox.boatbooster.BoatBooster;
import fr.codinbox.boatbooster.utils.EntityUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class VehicleListener implements Listener {

    private final @NotNull BoatBooster PLUGIN = BoatBooster.getInstance();

    @EventHandler
    private void onVehicleMove(final @NotNull VehicleMoveEvent event) {
        var vehicle = event.getVehicle();
        var loc = vehicle.getLocation();

        final var boosterConfig = PLUGIN.getBoosterConfig();
        if (boosterConfig == null)
            return;

        // Calculate velocity
        var from = event.getFrom();
        var to = event.getTo();
        var vector = new Vector(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());

        // Check block under the boat
        var blockUnder = loc.clone().add(0d, -0.25d, 0d);
        var blockUnderType = blockUnder.getBlock().getType().name();
        final var power = boosterConfig.getBumpers().get(blockUnderType);

        if (power != null) {
            EntityUtils.bump(vehicle, power, vector);
        }
    }

}
