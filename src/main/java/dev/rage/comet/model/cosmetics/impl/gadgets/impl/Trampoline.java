package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;

import java.util.List;
import java.util.Map;

public class Trampoline extends Gadget {

    private final ItemStack CENTER_BLOCK;

    public Trampoline(Comet plugin) {
        super(plugin);

        CENTER_BLOCK = XMaterial.BLUE_WOOL.parseItem();
    }

    @Override
    public String getName() {
        return "Trampoline";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(XMaterial.BLACK_BED.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("",
                        ChatColor.GRAY + "Right click to use this gadget!")
                .build();
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(XMaterial.BLACK_BED.parseMaterial())
                .setName(ChatColor.GREEN + getName())
                .setLore("")
                .build();
    }

    @Override
    public void onUsage(Player player) {
        player.getInventory().setItem(1, getItem());

        Location location = player.getLocation();

        Block stairs = location.add(location.getDirection()).getBlock().getRelative(BlockFace.UP);
        stairs.setType(XMaterial.OAK_STAIRS.parseMaterial());

        // set stairs look at me
        Stairs stairsData = (Stairs) stairs.getState().getData();
        stairsData.setFacingDirection(getBlockFace(player));
        stairs.setData(stairsData.getData());
        stairs.getState().update();

        Block stairs2 = stairs.getRelative(BlockFace.UP).getRelative(getBlockFace(player));
        stairs2.setType(XMaterial.OAK_STAIRS.parseMaterial());
        stairs2.setData(stairsData.getData());
        stairs2.getState().update();

        Block stairs3 = stairs2.getRelative(BlockFace.UP).getRelative(getBlockFace(player));
        stairs3.setType(XMaterial.OAK_STAIRS.parseMaterial());
        stairs3.setData(stairsData.getData());
        stairs3.getState().update();

        Material borderMaterial = XMaterial.BLACK_WOOL.parseMaterial();
        Block border1 = stairs.getRelative(getBlockFace(player));
        for (int index = 0; index < 25; index++) {
            if (index < 5 || index >= 25 - 5 || index % 5 == 0 || index % 5 == 4) {
                System.out.println(index);
            }
        }
    }

    private BlockFace getBlockFace(Player player) {
        float yaw = player.getLocation().getYaw();

        if (yaw < 0)
            yaw += 360;

        yaw %= 360;

        if (yaw < 45 || yaw > 315)
            return BlockFace.SOUTH;
        else if (yaw < 135)
            return BlockFace.WEST;
        else if (yaw < 225)
            return BlockFace.NORTH;
        else if (yaw < 315)
            return BlockFace.EAST;
        else
            return null;
    }
}
