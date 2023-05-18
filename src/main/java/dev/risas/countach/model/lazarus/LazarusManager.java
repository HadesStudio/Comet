package dev.risas.countach.model.lazarus;

import dev.risas.countach.Countach;
import dev.risas.countach.model.lazarus.impl.UserDataHCF;
import dev.risas.countach.model.lazarus.impl.UserDataKitMap;
import lombok.Getter;

/**
 * Created by Risas
 * Project: Countach
 * Date: 20-01-2023
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@Getter
public class LazarusManager {

    private final ILazarusUserData userDataKitMap, userDataHCF;

    public LazarusManager(Countach plugin) {
        this.userDataKitMap = new UserDataKitMap(plugin.getMongoManager().getMongo("kitmap"));
        this.userDataHCF = new UserDataHCF(plugin.getMongoManager().getMongo("hcf"));
    }
}
