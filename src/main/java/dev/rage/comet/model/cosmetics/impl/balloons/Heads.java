package dev.rage.comet.model.cosmetics.impl.balloons;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.rage.comet.utilities.ChatUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum Heads {

    DEV("Dev", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjA3MDc4NjE5MmVlOTlhNDI0OTYwNzUzN2VjMjg5YjE5YmVmMGRjZTJhNTUzZWMxZDFlZDg1ZGJjYTkyMTU0ZiJ9fX0=")
    ;

    private final String name, texture;

    public ItemStack getHeadSkull() {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setDisplayName(name);

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        skull.setItemMeta(skullMeta);
        return skull;
    }
}
