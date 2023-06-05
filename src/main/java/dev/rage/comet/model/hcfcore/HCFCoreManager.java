package dev.rage.comet.model.hcfcore;

import dev.rage.comet.Comet;
import dev.rage.comet.database.mongo.MongoManager;
import dev.rage.comet.model.hcfcore.azurite.AzuriteHCF;
import dev.rage.comet.model.hcfcore.azurite.AzuriteKitMap;
import dev.rage.comet.model.hcfcore.lazarus.LazarusHCF;
import dev.rage.comet.model.hcfcore.lazarus.LazarusKitMap;
import dev.rage.comet.utilities.ChatUtil;
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
            ChatUtil.logger("&c[" + plugin.getName() + "] HCFCore failed to connect.");
            return;
        }

        if (hcfCoreType == HCFCoreType.AZURITE) {
            this.kitmap = new AzuriteKitMap(mongoManager.getMongo("kitmap"));
            this.hcf = new AzuriteHCF(mongoManager.getMongo("hcf"));
        }
        else if (hcfCoreType == HCFCoreType.LAZARUS) {
            this.kitmap = new LazarusKitMap(mongoManager.getMongo("kitmap"));
            this.hcf = new LazarusHCF(mongoManager.getMongo("hcf"));
        }

        ChatUtil.logger("&a[" + plugin.getName() + "] HCFCore " + hcfCoreType.name() + " successfully connected.");
    }
}
