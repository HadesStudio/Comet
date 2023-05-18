package dev.rage.comet.model.lazarus;

import dev.rage.comet.Comet;
import dev.rage.comet.model.lazarus.impl.UserDataHCF;
import dev.rage.comet.model.lazarus.impl.UserDataKitMap;
import lombok.Getter;

/**
 * Created by Risas
 * Project: Comet
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class LazarusManager {

    private final ILazarusUserData userDataKitMap, userDataHCF;

    public LazarusManager(Comet plugin) {
        this.userDataKitMap = new UserDataKitMap(plugin.getMongoManager().getMongo("kitmap"));
        this.userDataHCF = new UserDataHCF(plugin.getMongoManager().getMongo("hcf"));
    }
}
