package dev.rage.comet.model.cosmetics.impl.mascots.impl;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.compatible.CompatibleParticle;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.UUID;

@Getter
public class Body {

    private final Heads head;
    private final String name, displayName;
    private ItemStack chestPlate;

    @Setter private boolean particle = false;
    @Setter private CompatibleParticle compatibleParticleEffect;

    public Body(Heads head, String name, String displayName, @Nullable ItemStack chestPlate) {
        this.head = head;
        this.name = name;
        this.displayName = displayName;

        if (chestPlate != null)
            this.chestPlate = chestPlate;
    }

    public void apply(Mascot mascot) {
        mascot.getArmorStand().setHelmet(getHeadSkull());

        if (chestPlate != null) mascot.getArmorStand().setChestplate(chestPlate);
    }

    public ItemStack getHeadSkull() {
        ItemStack skull = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.setDisplayName(ChatUtil.translate(displayName));

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", head.getTexture()));

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

    public boolean isUnlocked(Player player) {
        return player.isOp() || player.hasPermission("*") || player.hasPermission(getPermission());
    }

    public String getPermission() {
        return "balloons.mascot." + name.toLowerCase().replace(" ", "_");
    }
}
