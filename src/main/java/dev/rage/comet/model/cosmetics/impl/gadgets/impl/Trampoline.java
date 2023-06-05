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
        border1.setType(borderMaterial);

        Block border2 = border1.getRelative(getBlockFaceDirection(player, "RIGHT"));
        border2.setType(borderMaterial);

        Block border3 = border2.getRelative(getBlockFaceDirection(player, "FRONT"));
        border3.setType(borderMaterial);

        Block border4 = border3.getRelative(getBlockFaceDirection(player, "FRONT"));
        border4.setType(borderMaterial);

        Block border5 = border4.getRelative(getBlockFaceDirection(player, "FRONT"));
        border5.setType(borderMaterial);

        Block border6 = border5.getRelative(getBlockFaceDirection(player, "FRONT"));
        border6.setType(borderMaterial);

        Block border7 = border6.getRelative(getBlockFaceDirection(player, "FRONT"));
        border7.setType(borderMaterial);

        Block border8 = border7.getRelative(getBlockFaceDirection(player, "LEFT"));
        border8.setType(borderMaterial);

        Block border9 = border8.getRelative(getBlockFaceDirection(player, "LEFT"));
        border9.setType(borderMaterial);

        Block border10 = border9.getRelative(getBlockFaceDirection(player, "LEFT"));
        border10.setType(borderMaterial);

        Block border11 = border10.getRelative(getBlockFaceDirection(player, "LEFT"));
        border11.setType(borderMaterial);

        Block border12 = border11.getRelative(getBlockFaceDirection(player, "LEFT"));
        border12.setType(borderMaterial);

        Block border13 = border12.getRelative(getBlockFaceDirection(player, "BACK"));
        border13.setType(borderMaterial);

        Block border14 = border13.getRelative(getBlockFaceDirection(player, "BACK"));
        border14.setType(borderMaterial);

        Block border15 = border14.getRelative(getBlockFaceDirection(player, "BACK"));
        border15.setType(borderMaterial);

        Block border16 = border15.getRelative(getBlockFaceDirection(player, "BACK"));
        border16.setType(borderMaterial);

        Block border17 = border16.getRelative(getBlockFaceDirection(player, "BACK"));
        border17.setType(borderMaterial);

        Block border18 = border17.getRelative(getBlockFaceDirection(player, "RIGHT"));
        border18.setType(borderMaterial);

        Block border19 = border18.getRelative(getBlockFaceDirection(player, "RIGHT"));
        border19.setType(borderMaterial);
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

    private BlockFace getBlockFaceDirection(Player player, String direction) {
        BlockFace face = getBlockFace(player);

        switch (direction.toUpperCase()) {
            case "LEFT":
                switch (face) {
                    case NORTH:
                        return BlockFace.WEST;
                    case EAST:
                        return BlockFace.NORTH;
                    case SOUTH:
                        return BlockFace.EAST;
                    case WEST:
                        return BlockFace.SOUTH;
                }
                break;
            case "RIGHT":
                switch (face) {
                    case NORTH:
                        return BlockFace.EAST;
                    case EAST:
                        return BlockFace.SOUTH;
                    case SOUTH:
                        return BlockFace.WEST;
                    case WEST:
                        return BlockFace.NORTH;
                }
                break;
            case "FRONT":
                switch (face) {
                    case NORTH:
                        return BlockFace.NORTH;
                    case EAST:
                        return BlockFace.EAST;
                    case SOUTH:
                        return BlockFace.SOUTH;
                    case WEST:
                        return BlockFace.WEST;
                }
                break;
            case "BACK":
                switch (face) {
                    case NORTH:
                        return BlockFace.SOUTH;
                    case EAST:
                        return BlockFace.WEST;
                    case SOUTH:
                        return BlockFace.NORTH;
                    case WEST:
                        return BlockFace.EAST;
                }
                break;
            default:
                return face;
        }

        return face;
    }
}
