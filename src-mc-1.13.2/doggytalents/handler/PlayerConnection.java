package doggytalents.handler;

import doggytalents.ModItems;
import doggytalents.configuration.ConfigHandler;
import doggytalents.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerConnection {

	@SubscribeEvent
	public static void playerLoggedIn(final PlayerLoggedInEvent event) {
		EntityPlayer player = event.getPlayer();
		
		NBTTagCompound tag = player.getEntityData();

        if(!tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
        	tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        
        NBTTagCompound persistTag = tag.getCompound(EntityPlayer.PERSISTED_NBT_TAG);
        
		if(ConfigHandler.CONFIG.startingItems() && !persistTag.getBoolean("gotDTStartingItems")) {
			persistTag.setBoolean("gotDTStartingItems", true);

            player.inventory.addItemStackToInventory(new ItemStack(ModItems.DOGGY_CHARM));
            player.inventory.addItemStackToInventory(new ItemStack(ModItems.COMMAND_EMBLEM));
        }
	}
}