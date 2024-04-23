package dev.astro.net.utilities;

import com.cryptomorin.xseries.XBlock;
import com.cryptomorin.xseries.XMaterial;
import lombok.Data;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import javax.annotation.Nullable;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * ask first, and give me the credits.
 * Arigato! n.n
 */
@Data
public class FacedBlock {
    private final XMaterial xMaterial;
    private final @Nullable BlockFace blockFace;

    public void applyTo(Block block) {
        XBlock.setType(block, xMaterial);

        if (this.blockFace == null) {
            return;
        }

        XBlock.setDirection(block, this.blockFace);
    }
}