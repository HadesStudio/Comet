package dev.rage.comet.model.cosmetics.impl.outfits;

import com.google.common.collect.Lists;
import dev.rage.comet.Comet;
import dev.rage.comet.model.cosmetics.impl.outfits.entries.LeatherOutfit;
import dev.rage.comet.model.cosmetics.impl.outfits.impl.WhiteOutfit;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.LinkedList;

@Getter
public class OutfitManager {

    private LinkedList<LeatherOutfit> leatherOutfits;

    public OutfitManager(Comet plugin) {
        leatherOutfits = Lists.newLinkedList();

        loadOrRefresh();
    }

    private void loadOrRefresh() {
        leatherOutfits.add(new WhiteOutfit());
    }

    public void apply(Player player, LeatherOutfit leatherOutfit) {
        player.getInventory().setArmorContents(leatherOutfit.getArmor());
        player.sendMessage("You have equipped the " + leatherOutfit.getColor() + leatherOutfit.getName() + " Outfit.");
    }
}
