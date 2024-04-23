package dev.astro.net.model.cosmetics.impl.gadgets.impl;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import dev.astro.net.CometPlugin;
import dev.astro.net.utilities.Comet;
import dev.astro.net.model.cosmetics.impl.gadgets.Gadget;
import dev.astro.net.utilities.ChatUtil;
import dev.astro.net.utilities.item.ItemBuilder;
import dev.astro.net.utilities.Cuboid;
import dev.astro.net.utilities.FacedBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Trampoline extends Gadget {

    private final Map<UUID, List<Block>> blocks;
    private final List<Block> wools;

    public Trampoline(Comet plugin) {
        super(plugin);

        this.blocks = new ConcurrentHashMap<>();
        this.wools = new ArrayList<>();
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
        Location location = player.getLocation();
        Map<Block, FacedBlock> unsetBlocks = checkBlocks(location);
        List<Block> blocks = this.blocks.computeIfAbsent(player.getUniqueId(), ignored -> new ArrayList<>());
        List<Block> actualTrampolineBlocks = new ArrayList<>();

        // detect if you can place a trampoline in their location
        for (Block block : unsetBlocks.keySet()) {
            if (block.getType() != Material.AIR) {
                for (String s : plugin.getGadgetsFile().getStringList("trampoline.cant-use")) {
                    player.sendMessage(ChatUtil.translate(s));
                }
                return;
            }
        }

        Cuboid trampolineCuboid = new Cuboid(location.clone().add(-3, 0, -3), location.clone().add(3, 1, 3));
        location.getWorld().getPlayers().forEach(wp -> {
            if (!trampolineCuboid.contains(wp.getLocation())) {
                return;
            }
            wp.teleport(wp.getLocation().add(0, 2, 0));
        });

        unsetBlocks.forEach((block, facedBlock) -> {
            blocks.add(block);
            actualTrampolineBlocks.add(block);
            facedBlock.applyTo(block);
            if (block.getType().name().contains("WOOL")) {
                this.wools.add(block.getLocation().getBlock());
            }
        });

        CometPlugin.getPlugin().getServer().getScheduler().runTaskLater(CometPlugin.getPlugin(), () -> {
            for (Block block : actualTrampolineBlocks) {
                blocks.remove(block);
                this.wools.remove(block.getLocation().getBlock());
                block.setType(Material.AIR);
            }

            if (blocks.isEmpty()) { // If there are any other trampolines, just remove the data.
                this.blocks.remove(player.getUniqueId());
            }
        }, 20L * 15L);
    }

    @Override
    public void onRemoval(Player player) {

    }

    @Override
    public void onDisable() {
        blocks.forEach((uuid, blocks1) -> {
            blocks1.forEach(block -> {
                block.setType(Material.AIR);
            });
        });
    }

    private Map<Block, FacedBlock> checkBlocks(Location base) {
        Map<Block, FacedBlock> unsetBlocks = Maps.newHashMap();

        Location trampolineBase = base.clone().add(0, 1, 0);

        Cuboid border = new Cuboid(trampolineBase.clone().add(-3, 0, -3), trampolineBase.clone().add(3, 0, 3));
        Cuboid middle = new Cuboid(trampolineBase.clone().add(-2, 0, -2), trampolineBase.clone().add(2, 0, 2));
        border.getBlocks().forEach(block -> unsetBlocks.put(block, new FacedBlock(XMaterial.BLUE_WOOL, null)));
        middle.getBlocks().forEach(block -> unsetBlocks.put(block, new FacedBlock(XMaterial.BLACK_WOOL, null)));

        unsetBlocks.put(base.clone().add(3, 0, 3).getBlock(), new FacedBlock(XMaterial.GRAY_WOOL, null));
        unsetBlocks.put(base.clone().add(3, 0, -3).getBlock(), new FacedBlock(XMaterial.GRAY_WOOL, null));
        unsetBlocks.put(base.clone().add(-3, 0, 3).getBlock(), new FacedBlock(XMaterial.GRAY_WOOL, null));
        unsetBlocks.put(base.clone().add(-3, 0, -3).getBlock(), new FacedBlock(XMaterial.GRAY_WOOL, null));

        unsetBlocks.put(base.clone().add(0, 0, 5).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.NORTH));
        unsetBlocks.put(base.clone().add(5, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.WEST));
        unsetBlocks.put(base.clone().add(0, 0, -5).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.SOUTH));
        unsetBlocks.put(base.clone().add(-5, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.EAST));

        unsetBlocks.put(base.clone().add(0, 0, 4).getBlock(), new FacedBlock(XMaterial.OAK_PLANKS, null));
        unsetBlocks.put(base.clone().add(4, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_PLANKS, null));
        unsetBlocks.put(base.clone().add(0, 0, -4).getBlock(), new FacedBlock(XMaterial.OAK_PLANKS, null));
        unsetBlocks.put(base.clone().add(-4, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_PLANKS, null));

        unsetBlocks.put(trampolineBase.clone().add(0, 0, 4).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.NORTH));
        unsetBlocks.put(trampolineBase.clone().add(4, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.WEST));
        unsetBlocks.put(trampolineBase.clone().add(0, 0, -4).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.SOUTH));
        unsetBlocks.put(trampolineBase.clone().add(-4, 0, 0).getBlock(), new FacedBlock(XMaterial.OAK_STAIRS, BlockFace.EAST));

        return unsetBlocks;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ((int) event.getTo().getX() == (int) event.getFrom().getX() &&
                (int) event.getTo().getY() == (int) event.getFrom().getZ() &&
                (int) event.getTo().getZ() == (int) event.getFrom().getZ())
            return;

        Player player = event.getPlayer();

        if (wools.contains(player.getLocation().add(0, -1, 0).getBlock())) {
            player.setVelocity(new Vector(0, 3, 0));
        }
    }
}