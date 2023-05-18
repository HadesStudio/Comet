package dev.rage.comet.tablist.utilities;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Created by Risas
 * Project: PandaHub
 * Date: 11-11-2021 - 19:56
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class TablistUtil {

    public String getListFromString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            stringBuilder.append(list.get(i));
            if (i != list.size() - 1) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
