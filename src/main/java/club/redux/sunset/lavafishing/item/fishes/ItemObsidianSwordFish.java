package club.redux.sunset.lavafishing.item.fishes;

import club.redux.sunset.lavafishing.util.RegistryCollection.ItemCollection;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ItemObsidianSwordFish extends SwordItem
{
    public enum ModItemTier implements Tier
    {
        FISH(0, 0, 2.0F, 0.0F, 15, () -> Ingredient.of(ItemCollection.ITEM_OBSIDIAN_SWORD_FISH.get()), null);
        private final int level;
        private final int maxUses;
        private final float speed;
        private final float attackDamage;
        private final int enchantmentValue;
        private final Supplier<Ingredient> repairIngredient;
        private final TagKey<Block> tag;

        ModItemTier(int level, int maxUses, float speed, float attackDamage, int enchantmentValue, Supplier<Ingredient> repairIngredient, TagKey<Block> tag) {
            this.level = level;
            this.maxUses = maxUses;
            this.speed = speed;
            this.attackDamage = attackDamage;
            this.enchantmentValue = enchantmentValue;
            this.repairIngredient = repairIngredient;
            this.tag = tag;
        }


        @Override
        public int getUses() {return this.maxUses;}

        @Override
        public float getSpeed() {return this.speed;}

        @Override
        public float getAttackDamageBonus() {return this.attackDamage;}

        @Override
        public int getLevel() {return this.level;}

        @Override
        public int getEnchantmentValue() {return this.enchantmentValue;}

        @NotNull
        @Override
        public Ingredient getRepairIngredient() {
//            return this.repairIngredient.get();
            return null;
        }

        @Nullable
        @Override
        public TagKey<Block> getTag() {return this.tag;}
    }

    public ItemObsidianSwordFish() {
        super(ModItemTier.FISH, 2, 2, GetProperties());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        pLivingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300));
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    public static Properties GetProperties() {
        FoodProperties foodProperties = new FoodProperties.Builder()
                .nutrition(3)
                .saturationMod(0.6F)
                .build();
        return new Properties()
        {
            @Override
            public Item.Properties durability(int pMaxDamage) {
                super.durability(pMaxDamage);
                stacksTo(64);
                return this;
            }
        }
                .food(foodProperties)
                .fireResistant()
                .setNoRepair();
    }
}
