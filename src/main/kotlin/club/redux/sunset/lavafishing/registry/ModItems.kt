package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.LavaFishing
import club.redux.sunset.lavafishing.entity.EntityAmphibious
import club.redux.sunset.lavafishing.entity.EntityCommonFish
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.item.ItemLavaFishingRod
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.bullet.ItemPromethiumBullet
import club.redux.sunset.lavafishing.item.cuisine.ItemSimpleFood
import club.redux.sunset.lavafishing.item.cuisine.ItemSpicyFishFillet
import club.redux.sunset.lavafishing.item.fish.*
import club.redux.sunset.lavafishing.item.slingshot.ItemNeptuniumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.misc.ModArmorMaterials
import club.redux.sunset.lavafishing.misc.ModToolMaterials
import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.teammetallurgy.aquaculture.item.FishItem
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.MobBucketItem
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluids

object ModItems : Registrar<Item>(BuiltInRegistries.ITEM, BuiltConstants.MOD_ID) {

    // Fishing Rods
    val OBSIDIAN_FISHING_ROD by this.register { ItemLavaFishingRod(ModToolMaterials.OBSIDIAN, properties(it)) }
    val NETHERITE_FISHING_ROD by this.register { ItemLavaFishingRod(ToolMaterial.NETHERITE, properties(it)) }

    // Fish
    val FLAME_SQUAT_LOBSTER by this.registerFish(::EntityAmphibious, LavaFishType.LOBSTER, ::ItemFlameSquatLobster)
    val OBSIDIAN_SWORD_FISH by this.registerFish(::EntityCommonFish, LavaFishType.SWORDFISH, ::ItemObsidianSwordFish)
    val STEAM_FLYING_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON, ::ItemSteamFlyingFish)
    val AGNI_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON, ::ItemAgniFish)
    val AROWANA_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON, ::ItemLavaFish)
    val QUARTZ_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON, ::ItemLavaFish)
    val SCALY_FOOT_SNAIL by this.registerFish(::EntityAmphibious, LavaFishType.SNAIL) {
        ItemLavaFish(it, FishItem.SMALL_FISH_RAW)
    }
    val YETI_CRAB by this.registerFish(::EntityAmphibious, LavaFishType.CRAB) {
        ItemLavaFish(it, FishItem.SMALL_FISH_RAW)
    }
    val LAVA_LAMPREY by this.registerFish(::EntityCommonFish, LavaFishType.EEL, ::ItemLavaFish)

    // Food
    val SPICY_FISH_FILLET by this.register { ItemSpicyFishFillet(properties(it)) }
    val FISH_PASTE by this.register { ItemSimpleFood(properties(it)) { nutrition(7).saturationModifier(0.5f) } }

    // Armor
    val PROMETHIUM_HELMET by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorType.HELMET, properties(it))
    }
    val PROMETHIUM_CHESTPLATE by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorType.CHESTPLATE, properties(it))
    }
    val PROMETHIUM_LEGGINGS by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorType.LEGGINGS, properties(it))
    }
    val PROMETHIUM_BOOTS by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorType.BOOTS, properties(it))
    }

    // Slingshot
    val IRON_SLINGSHOT by this.register { ItemSlingshot(ToolMaterial.IRON, properties(it)) }
    val NEPTUNIUM_SLINGSHOT by this.register { ItemNeptuniumSlingshot(properties(it)) }
    val PROMETHIUM_SLINGSHOT by this.register { ItemPromethiumSlingshot(properties(it)) }
    val STONE_BULLET by this.register { ItemBullet(properties(it)) { ModEntityTypes.STONE_BULLET.get() } }
    val IRON_BULLET by this.register { ItemBullet(properties(it)) { ModEntityTypes.IRON_BULLET.get() } }
    val NEPTUNIUM_BULLET by this.register { ItemBullet(properties(it)) { ModEntityTypes.NEPTUNIUM_BULLET.get() } }
    val PROMETHIUM_BULLET by this.register { ItemPromethiumBullet(properties(it)) }

    // Other
    val PROMETHIUM_INGOT by this.register { Item(properties(it).fireResistant()) }
    val PROMETHIUM_NUGGET by this.register { Item(properties(it).fireResistant()) }
    val PROMETHIUM_BLOCK by this.register {
        BlockItem(ModBlocks.PROMETHIUM_BLOCK.get(), properties(it).fireResistant())
    }
    val PROMETHEUS_BOUNTY by this.register {
        BlockItem(ModBlocks.PROMETHEUS_BOUNTY.get(), properties(it).fireResistant())
    }

    private fun <T : ItemLavaFish> registerFish(
        fishConstructor: (EntityType<EntityLavaFish>, Level, LavaFishType) -> EntityLavaFish,
        fishType: LavaFishType,
        itemSupplier: (Item.Properties) -> T,
    ) = this.register { itemSupplier(properties(it)) }.pre {
        val fish = ModEntityTypes.registerWithMap<EntityLavaFish>(it) {
            EntityType.Builder.of(
                { f: EntityType<EntityLavaFish>, w: Level -> fishConstructor(f, w, fishType) },
                MobCategory.WATER_AMBIENT
            ).sized(fishType.width, fishType.height)
                .build(ResourceKey.create(Registries.ENTITY_TYPE, LavaFishing.identifier(it)))
        }

        //Registers fish buckets
        (it + "_bucket").register {
            MobBucketItem(
                fish.get(),
                Fluids.LAVA,
                SoundEvents.BUCKET_EMPTY_FISH,
                properties(it + "_bucket").stacksTo(1)
            )
        }
    }

    private fun properties(name: String) = Item.Properties().setId(this.createResourceKey(name))
}
