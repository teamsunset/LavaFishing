package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuiltConstants
import club.redux.sunset.lavafishing.block.blockentity.BlockEntityPrometheusBounty
import club.redux.sunset.lavafishing.entity.EntityAmphibious
import club.redux.sunset.lavafishing.entity.EntityCommonFish
import club.redux.sunset.lavafishing.entity.EntityLavaFish
import club.redux.sunset.lavafishing.item.ItemFishingRod
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.block.BlockItemWithoutLevelRenderer
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.bullet.ItemPromethiumBullet
import club.redux.sunset.lavafishing.item.cuisine.ItemSimpleFood
import club.redux.sunset.lavafishing.item.cuisine.ItemSpicyFishFillet
import club.redux.sunset.lavafishing.item.fish.*
import club.redux.sunset.lavafishing.item.slingshot.ItemNeptuniumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem
import com.teammetallurgy.aquaculture.item.AquaFishingRodItem.FishingRodEquipmentHandler
import com.teammetallurgy.aquaculture.item.FishItem.SMALL_FISH_RAW
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluids
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.neoforge.capabilities.Capabilities
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent

object ModItems : Registrar<Item>(BuiltInRegistries.ITEM, BuiltConstants.MOD_ID) {

    // Fishing Rods
    val OBSIDIAN_FISHING_ROD by this.register { ItemFishingRod(ModTiers.OBSIDIAN) }
    val NETHERITE_FISHING_ROD by this.register { ItemFishingRod(Tiers.NETHERITE) { fireResistant() } }

    // Fish
    val FLAME_SQUAT_LOBSTER by this.registerFish(::EntityAmphibious, LavaFishType.LOBSTER) { ItemFlameSquatLobster() }
    val OBSIDIAN_SWORD_FISH by this.registerFish(::EntityCommonFish, LavaFishType.SWORDFISH) { ItemObsidianSwordFish() }
    val STEAM_FLYING_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON) { ItemSteamFlyingFish() }
    val AGNI_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON) { ItemAgniFish() }
    val AROWANA_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON) { ItemLavaFish() }
    val QUARTZ_FISH by this.registerFish(::EntityCommonFish, LavaFishType.COMMON) { ItemLavaFish() }
    val SCALY_FOOT_SNAIL by this.registerFish(::EntityAmphibious, LavaFishType.SNAIL) { ItemLavaFish(SMALL_FISH_RAW) }
    val YETI_CRAB by this.registerFish(::EntityAmphibious, LavaFishType.CRAB) { ItemLavaFish(SMALL_FISH_RAW) }
    val LAVA_LAMPREY by this.registerFish(::EntityCommonFish, LavaFishType.EEL) { ItemLavaFish() }

    // Food
    val SPICY_FISH_FILLET by this.register { ItemSpicyFishFillet() }
    val FISH_PASTE by this.register { ItemSimpleFood { nutrition(7).saturationModifier(0.5f) } }

    // Armor
    val PROMETHIUM_HELMET by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1")
    }
    val PROMETHIUM_CHESTPLATE by this.register {
        ItemPromethiumArmor(
            ModArmorMaterials.PROMETHIUM,
            ArmorItem.Type.CHESTPLATE
        ).setArmorTexture("promethium_layer_1")
    }
    val PROMETHIUM_LEGGINGS by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2")
    }
    val PROMETHIUM_BOOTS by this.register {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1")
    }

    // Slingshot
    val IRON_SLINGSHOT by this.register { ItemSlingshot(Tiers.IRON, Properties()) }
    val NEPTUNIUM_SLINGSHOT by this.register { ItemNeptuniumSlingshot() }
    val PROMETHIUM_SLINGSHOT by this.register { ItemPromethiumSlingshot() }
    val STONE_BULLET by this.register { ItemBullet(Properties()) { ModEntityTypes.STONE_BULLET.get() } }
    val IRON_BULLET by this.register { ItemBullet(Properties()) { ModEntityTypes.IRON_BULLET.get() } }
    val NEPTUNIUM_BULLET by this.register { ItemBullet(Properties()) { ModEntityTypes.NEPTUNIUM_BULLET.get() } }
    val PROMETHIUM_BULLET by this.register { ItemPromethiumBullet() }

    // Other
    val PROMETHIUM_INGOT by this.register { Item(Properties().fireResistant()) }
    val PROMETHIUM_NUGGET by this.register { Item(Properties().fireResistant()) }
    val PROMETHIUM_BLOCK by this.register { BlockItem(ModBlocks.PROMETHIUM_BLOCK.get(), Properties().fireResistant()) }
    val PROMETHEUS_BOUNTY by this.register {
        BlockItemWithoutLevelRenderer(ModBlocks.PROMETHEUS_BOUNTY.get(), Properties().fireResistant()) {
            BlockEntityPrometheusBounty(BlockPos.ZERO, ModBlocks.PROMETHEUS_BOUNTY.get().defaultBlockState())
        }
    }

    private fun <T : ItemLavaFish> registerFish(
        fishConstructor: (EntityType<EntityLavaFish>, Level, LavaFishType) -> EntityLavaFish,
        fishType: LavaFishType,
        itemSupplier: (String) -> T,
    ) = this.register(itemSupplier).pre {
        val fish = ModEntityTypes.registerWithMap(it) {
            EntityType.Builder.of(
                { f: EntityType<EntityLavaFish>, w: Level -> fishConstructor(f, w, fishType) },
                MobCategory.WATER_AMBIENT
            ).sized(fishType.width, fishType.height).build(BuiltConstants.MOD_ID + ":" + this)
        }

        //Registers fish buckets
        (it + "_bucket").register {
            MobBucketItem(
                fish.get(),
                Fluids.LAVA,
                SoundEvents.BUCKET_EMPTY_FISH,
                Properties().stacksTo(1)
            )
        }
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            this.getEntriesIsInstance<FishingRodItem>().forEach {
                ClientHandler.registerFishingRodModelProperties(it)
            }
        }
    }

    fun onRegisterCapabilities(event: RegisterCapabilitiesEvent) {
        event.registerItem(
            Capabilities.ItemHandler.ITEM,
            { stack: ItemStack?, _: Any? -> FishingRodEquipmentHandler(stack) },
            *this.getEntriesIsInstance<AquaFishingRodItem>().toTypedArray()
        )
    }
}