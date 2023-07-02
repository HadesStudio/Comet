package dev.rage.comet.model.cosmetics.impl.gadgets.impl;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.gadgets.Gadget;
import dev.rage.comet.utilities.ChatUtil;
import dev.rage.comet.utilities.item.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.util.Vector;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Trampoline extends Gadget {

    private final Map<UUID, List<Block>> blocks;
    private final Set<Block> allBlocks;

    public Trampoline(Comet plugin) {
        super(plugin);

        blocks = Maps.newHashMap();
        allBlocks = Sets.newConcurrentHashSet();
    }

    @Override
    public String getName() {
        return "Trampoline";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(com.cryptomorin.xseries.XMaterial.BLACK_BED.parseMaterial())
                .setName(plugin.getGadgetsFile().getString("menu.name_format")
                        .replace("<name>", getName()))
                .setLore(plugin.getGadgetsFile().getStringList("menu.trampoline_lore"))
                .build();
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public void onUsage(Player player) {
        //player.getInventory().setItem(1, getItem());

        Map<Block, String> unsetBlocks = checkBlocks(player);

        List<BlockState> blocks = Lists.newArrayList();

        // detect if you can place a trampoline in their location
        for (Block block : unsetBlocks.keySet()) {
            if (block.getType() != Material.AIR) {
                for (String s : plugin.getGadgetsFile().getStringList("trampoline.cant-use")) {
                    player.sendMessage(ChatUtil.translate(s));
                }
                return;
            }
        }

        com.cryptomorin.xseries.XMaterial woolMaterial = com.cryptomorin.xseries.XMaterial.WHITE_WOOL;

        unsetBlocks.forEach((block, s) -> {
            allBlocks.add(block);
            switch (s) {
                case "STAIRS":
                    setFacingStair(blocks, block, player);
                    break;
                case "BORDER":
                    setBlock(blocks, block, woolMaterial, (byte) 15);
                    break;
                case "FILL":
                    setBlock(blocks, block, woolMaterial, (byte) 11);
                    break;
            }
        });

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            for (Block block : unsetBlocks.keySet()) {
                allBlocks.remove(block);
            }

            for (BlockState block : blocks) {
                block.update(true);
            }

            this.blocks.remove(player.getUniqueId());
        }, 20L * 15L);
    }

    @Override
    public void onDisable() {
        for (Block block : allBlocks) {
            block.setType(Material.AIR);
        }

        allBlocks.clear();
    }

    private Map<Block, String> checkBlocks(Player player) {
        Map<Block, String> unsetBlocks = Maps.newHashMap();

        Location location = player.getLocation();
        Block stairs = location.add(location.getDirection()).getBlock().getRelative(BlockFace.UP);

        for (int i = 0; i < 2; i++) {
            if (i != 0) {
                stairs = stairs.getRelative(BlockFace.UP).getRelative(getBlockFace(player));
            }

            unsetBlocks.put(stairs, "STAIRS");
        }

        Iterator<String> aimPoints = Iterators.forArray("RIGHT", "FRONT", "LEFT", "BACK");
        Block border = null;

        String aimPoint = null;
        for (int i = 0; i < 24; i++) {
            if (i == 0) {
                border = stairs.getRelative(getBlockFace(player))
                        .getRelative(getBlockFaceDirection(player, "LEFT"))
                        .getRelative(getBlockFaceDirection(player, "LEFT"))
                        .getRelative(getBlockFaceDirection(player, "LEFT"));
            }

            if (i % 6 == 0) {
                aimPoint = aimPoints.next();
            }

            border = border.getRelative(getBlockFaceDirection(player, aimPoint));
            unsetBlocks.put(border, "BORDER");
        }

        // Fill per lines

        Block startPoint = null;
        Block fill1 = null;
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                startPoint = stairs.getRelative(getBlockFace(player))
                        .getRelative(getBlockFace(player))
                        .getRelative(getBlockFaceDirection(player, "LEFT"))
                        .getRelative(getBlockFaceDirection(player, "LEFT"));
                unsetBlocks.put(startPoint, "FILL");

                fill1 = startPoint;
                continue;
            }

            if (i % 5 == 0) {
                startPoint = startPoint.getRelative(getBlockFaceDirection(player, "FRONT"));
                unsetBlocks.put(startPoint, "FILL");

                fill1 = startPoint;
            } else {
                fill1 = fill1.getRelative(getBlockFaceDirection(player, "RIGHT"));
                unsetBlocks.put(fill1, "FILL");
            }

            if (!this.blocks.containsKey(player.getUniqueId())) {
                this.blocks.put(player.getUniqueId(), Lists.newArrayList());
            }

            List<Block> playerTrampolineBlocks = this.blocks.get(player.getUniqueId());
            playerTrampolineBlocks.add(fill1);

            this.blocks.put(player.getUniqueId(), playerTrampolineBlocks);
        }

        return unsetBlocks;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ((int) event.getTo().getX() == (int) event.getFrom().getX() &&
                (int) event.getTo().getY() == (int) event.getFrom().getZ() &&
                (int) event.getTo().getZ() == (int) event.getFrom().getZ())
            return;

        Player player = event.getPlayer();

        if (checkTrampoline(player)) {
            player.setVelocity(new Vector(0, 15, 0));
        }
    }

    private void setBlock(List<BlockState> blocks, Block block, com.cryptomorin.xseries.XMaterial material, byte data) {
        blocks.add(block.getState());

        block.setType(material.parseMaterial());
        block.setData(data);

        allBlocks.add(block);
    }

    private boolean checkTrampoline(Player player) {
        Block relative = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        for (List<Block> value : blocks.values()) {
            if (value.contains(relative)) {
                return true;
            }
        }

        return false;
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

    private void setFacingStair(List<BlockState> list, Block block, Player player) {
        list.add(block.getState());

        block.setType(com.cryptomorin.xseries.XMaterial.OAK_STAIRS.parseMaterial());

        Stairs stairsData = (Stairs) block.getState().getData();
        stairsData.setFacingDirection(getBlockFace(player));

        block.setData(stairsData.getData());
        block.getState().update();

        allBlocks.add(block);
    }
}
