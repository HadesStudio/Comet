package dev.astro.net.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by AstroDev
 * Project: CometMC
 * Date: 02-02-2022
 * Twitter: @AstroDev
 * GitHub: https://github.com/AstroDev
 */

@UtilityClass
public class BukkitUtil {

    public String getLocation(Location location) {
        if (location == null) return null;
        return location.getWorld().getName() + ", "
                + location.getX() + ", "
                + location.getY() + ", "
                + location.getZ();
    }

    public String serializeLocation(Location location) {
        if (location == null) return null;
        return location.getWorld().getName() + ", " +
                location.getX() + ", " +
                location.getY() + ", " +
                location.getZ() + ", " +
                location.getYaw() + ", " +
                location.getPitch();
    }

    public Location deserializeLocation(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] splittedData = data.split(", ");

        if (splittedData.length < 6) return null;

        World world = Bukkit.getWorld(splittedData[0]);
        double x = Double.parseDouble(splittedData[1]);
        double y = Double.parseDouble(splittedData[2]);
        double z = Double.parseDouble(splittedData[3]);
        float yaw = Float.parseFloat(splittedData[4]);
        float pitch = Float.parseFloat(splittedData[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public String serializeBlockLocation(Location location) {
        if (location == null || location.getWorld() == null) return null;
        return location.getWorld().getName() + ", " +
                location.getBlockX() + ", " +
                location.getBlockY() + ", " +
                location.getBlockZ();
    }

    public Location deserializeBlockLocation(String data) {
        if (data == null || data.isEmpty()) return null;

        String[] splittedData = data.split(", ");

        if (splittedData.length < 4) return null;

        World world = Bukkit.getWorld(splittedData[0]);
        double x = Double.parseDouble(splittedData[1]);
        double y = Double.parseDouble(splittedData[2]);
        double z = Double.parseDouble(splittedData[3]);

        return new Location(world, x, y, z);
    }

    public Collection<String> serializeLocations(Collection<Location> locations) {
        if (locations == null) return null;

        List<String> serializedLocations = new ArrayList<>();

        for (Location location : locations) {
            serializedLocations.add(serializeBlockLocation(location));
        }

        return serializedLocations;
    }

    public Collection<Location> deserializeLocations(Collection<String> data) {
        if (data == null) return null;

        List<Location> locations = new ArrayList<>();

        for (String location : data) {
            locations.add(deserializeBlockLocation(location));
        }

        return locations;
    }

    public String serializeItemStackArray(ItemStack[] itemStacks) {
        if (itemStacks == null) return null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(itemStacks.length);

            for (ItemStack itemStack : itemStacks) {
                dataOutput.writeObject(itemStack);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public ItemStack[] deserializeItemStackArray(String data) {
        if (data == null) return null;
        if (data.equals("")) return null;

        try {
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(Base64Coder.decodeLines(data)));
            ItemStack[] items = new ItemStack[dataInput.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }

            dataInput.close();
            return items;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
