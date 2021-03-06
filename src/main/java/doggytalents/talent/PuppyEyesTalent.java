package doggytalents.talent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import doggytalents.api.inferface.IDogEntity;
import doggytalents.api.inferface.Talent;
import doggytalents.helper.DogUtil;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author ProPercivalalb
 */
public class PuppyEyesTalent extends Talent {

    @Override
    public void onClassCreation(IDogEntity dog) {
        dog.putObject("charmercharge", 0);
        dog.putObject("villagersorter", new DogUtil.Sorter(dog));
    }

    @Override
    public void writeAdditional(IDogEntity dog, CompoundNBT tagCompound) {
        int charmerCharge = dog.getObject("charmercharge", Integer.TYPE);
        tagCompound.putInt("charmercharge", charmerCharge);
    }

    @Override
    public void readAdditional(IDogEntity dog, CompoundNBT tagCompound) {
        dog.putObject("charmercharge", tagCompound.getInt("charmercharge"));
    }

    @Override
    public void livingTick(IDogEntity dog) {
        if(!dog.isTamed()) return;

        int charmerCharge = dog.getObject("charmercharge", Integer.TYPE);
        if(charmerCharge > 0) {
            charmerCharge -= 1;
            dog.putObject("charmercharge", charmerCharge);
        }

        int level = dog.getTalentFeature().getLevel(this);
        PlayerEntity player = (PlayerEntity)dog.getOwner();

        if(!dog.world.isRemote && dog.getTalentFeature().getLevel(this) != 0 && charmerCharge == 0) {
            LivingEntity entityliving = this.charmVillagers(dog, 5D);

            if(entityliving != null && player != null) {
                int j1 = dog.getRNG().nextInt(level) + (level != 5 ? 0 : 1);

                if(j1 == 0) {
                    player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.1.line.1", dog.getGenderFeature().getGenderPronoun()));
                    player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.1.line.2", dog.getGenderFeature().getGenderSubject()));
                    entityliving.entityDropItem(Items.PORKCHOP, 2);
                } else if(j1 == 1) {
                      player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.2.line.1", dog.getGenderFeature().getGenderTitle()));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.2.line.2", dog.getGenderFeature().getGenderTitle()));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.2.line.3", dog.getGenderFeature().getGenderTitle()));
                    entityliving.entityDropItem(Items.PORKCHOP, 5);
                } else if(j1 == 2) {
                    player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.3.line.1"));
                    player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.3.line.2"));
                    player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.3.line.3"));
                    entityliving.entityDropItem(Items.IRON_INGOT, 3);
                } else if(j1 == 3) {
                     player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.4.line.1"));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.4.line.2"));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.4.line.3"));
                    entityliving.entityDropItem(Items.GOLD_INGOT, 2);
                } else if(j1 == 4) {
                     player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.5.line.1"));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.5.line.2"));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.5.line.3"));
                    entityliving.entityDropItem(Items.DIAMOND, 1);
                } else if(j1 == 5) {
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.6.line.1"));
                       player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.6.line.2"));
                      player.sendMessage(new TranslationTextComponent("talent.doggytalents.puppy_eyes.msg.6.line.3"));
                    entityliving.entityDropItem(Items.APPLE, 1);
                    entityliving.entityDropItem(Blocks.CAKE, 1);
                    entityliving.entityDropItem(Items.SLIME_BALL, 3);
                    entityliving.entityDropItem(Items.PORKCHOP, 5);
                }

                dog.putObject("charmercharge", level != 5 ? 48000 : 24000);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public LivingEntity charmVillagers(IDogEntity dogIn, double radiusIn) {
        List<AbstractVillagerEntity> list = dogIn.world.getEntitiesWithinAABB(AbstractVillagerEntity.class, dogIn.getBoundingBox().grow(radiusIn, radiusIn, radiusIn), village -> village.canEntityBeSeen(dogIn));
        Collections.sort(list, (Comparator<Entity>) dogIn.getObject("villagersorter", Comparator.class));

        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
