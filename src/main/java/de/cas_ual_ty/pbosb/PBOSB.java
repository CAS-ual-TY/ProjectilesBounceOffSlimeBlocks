package de.cas_ual_ty.pbosb;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(PBOSB.MOD_ID)
public class PBOSB
{
    public static final String MOD_ID = "pbosb";
    
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public PBOSB()
    {
        MinecraftForge.EVENT_BUS.addListener(this::projectileImpact);
    }
    
    private void projectileImpact(ProjectileImpactEvent event)
    {
        if(event.getRayTraceResult().getType() == HitResult.Type.BLOCK && event.getRayTraceResult() instanceof BlockHitResult hit)
        {
            Level level = event.getProjectile().level;
            BlockPos pos = hit.getBlockPos();
            BlockState blockState = level.getBlockState(pos);
            
            if(blockState.getBlock() == Blocks.SLIME_BLOCK)
            {
                Direction d = hit.getDirection();
                
                Vec3 mot = event.getProjectile().getDeltaMovement();
                
                double x = mot.x();
                double y = mot.y();
                double z = mot.z();
                
                if(d.getStepX() != 0)
                {
                    x *= -1D;
                }
                else if(d.getStepY() != 0)
                {
                    y *= -1D;
                }
                else if(d.getStepZ() != 0)
                {
                    z *= -1D;
                }
                
                Vec3 newMot = new Vec3(x, y, z);
                
                event.getProjectile().setPos(hit.getLocation());
                event.getProjectile().setDeltaMovement(newMot);
                event.setCanceled(true);
            }
        }
    }
}
