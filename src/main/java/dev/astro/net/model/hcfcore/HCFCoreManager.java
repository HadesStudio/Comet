package dev.astro.net.model.hcfcore;

import dev.astro.net.CometPlugin;
import dev.astro.net.model.hcfcore.vapor.VaporHCF;
import dev.astro.net.model.hcfcore.vapor.VaporKitMap;
import dev.astro.net.utilities.Comet;
import dev.astro.net.database.mongo.MongoManager;
import dev.astro.net.model.hcfcore.azurite.AzuriteHCF;
import dev.astro.net.model.hcfcore.azurite.AzuriteKitMap;
import dev.astro.net.model.hcfcore.lazarus.LazarusHCF;
import dev.astro.net.model.hcfcore.lazarus.LazarusKitMap;
import dev.astro.net.utilities.ChatUtil;
import lombok.Getter;

@Getter
public class HCFCoreManager {

    private IHCFCore kitmap, hcf;

    public HCFCoreManager(Comet plugin) {
        MongoManager mongoManager = plugin.getMongoManager();
        HCFCoreType hcfCoreType;

        try {
            hcfCoreType = HCFCoreType.valueOf(plugin.getConfigFile().getString("hcfcore-type").toUpperCase());
        }
        catch (Exception ex) {
            ChatUtil.logger("&c[" + CometPlugin.getPlugin().getName() + "] HCFCore failed to connect.");
            return;
        }

        if (hcfCoreType == HCFCoreType.VAPOR) {
            this.kitmap = new VaporKitMap(mongoManager.getMongo("kitmap"));
            this.hcf = new VaporHCF(mongoManager.getMongo("hcf"));
        }
        else if (hcfCoreType == HCFCoreType.AZURITE) {
            this.kitmap = new AzuriteKitMap(mongoManager.getMongo("kitmap"));
            this.hcf = new AzuriteHCF(mongoManager.getMongo("hcf"));
        }
        else if (hcfCoreType == HCFCoreType.LAZARUS) {
            this.kitmap = new LazarusKitMap(mongoManager.getMongo("kitmap"));
            this.hcf = new LazarusHCF(mongoManager.getMongo("hcf"));
        }


        ChatUtil.logger("&a[" + CometPlugin.getPlugin().getName() + "] HCFCore " + hcfCoreType.name() + " successfully connected.");
    }
}
