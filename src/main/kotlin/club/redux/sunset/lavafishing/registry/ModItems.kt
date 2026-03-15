package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
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
import club.redux.sunset.lavafishing.item.fish.ItemAgniFish
import club.redux.sunset.lavafishing.item.fish.ItemFlameSquatLobster
import club.redux.sunset.lavafishing.item.fish.ItemLavaFish
import club.redux.sunset.lavafishing.item.fish.ItemObsidianSwordFish
import club.redux.sunset.lavafishing.item.fish.ItemSteamFlyingFish
import club.redux.sunset.lavafishing.item.slingshot.ItemNeptuniumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemSlingshot
import club.redux.sunset.lavafishing.misc.LavaFishType
import club.redux.sunset.lavafishing.misc.ModArmorMaterials
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.tool.registry.Registrar
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.api.fishing.Hook
import com.teammetallurgy.aquaculture.api.fishing.Hook.HookBuilder
import com.teammetallurgy.aquaculture.client.ClientHandler
import com.teammetallurgy.aquaculture.item.FishItem.SMALL_FISH_RAW
import com.teammetallurgy.aquaculture.item.HookItem
import net.minecraft.ChatFormatting
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.tags.FluidTags
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.MobCategory
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.FishingRodItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.MobBucketItem
import net.minecraft.world.item.Tiers
import net.minecraft.world.level.Level
import net.minecraft.world.level.material.Fluids
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject

object ModItems : Registrar<Item>(ForgeRegistries.ITEMS, BuildConstants.MOD_ID) {
    @JvmField
    val OBSIDIAN_FISHING_ROD = this.register("obsidian_fishing_rod") { ItemFishingRod(ModTiers.OBSIDIAN) }

    @JvmField
    val NETHERITE_FISHING_ROD = this.register("netherite_fishing_rod") {
        ItemFishingRod(Tiers.NETHERITE) { fireResistant() }
    }

    @JvmField
    val FLAME_SQUAT_LOBSTER = this.registerFish(
        "flame_squat_lobster",
        EntityAmphibious::class.java,
        ::EntityAmphibious,
        LavaFishType.LOBSTER,
    ) { ItemFlameSquatLobster() }

    @JvmField
    val OBSIDIAN_SWORD_FISH = this.registerFish(
        "obsidian_sword_fish",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.SWORDFISH,
    ) { ItemObsidianSwordFish() }

    @JvmField
    val STEAM_FLYING_FISH = this.registerFish(
        "steam_flying_fish",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.COMMON,
    ) { ItemSteamFlyingFish() }

    @JvmField
    val AGNI_FISH = this.registerFish(
        "agni_fish",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.COMMON,
    ) { ItemAgniFish() }

    @JvmField
    val AROWANA_FISH = this.registerFish(
        "arowana_fish",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.COMMON,
    ) { ItemLavaFish() }

    @JvmField
    val QUARTZ_FISH = this.registerFish(
        "quartz_fish",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.COMMON,
    ) { ItemLavaFish() }

    @JvmField
    val SCALY_FOOT_SNAIL = this.registerFish(
        "scaly_foot_snail",
        EntityAmphibious::class.java,
        ::EntityAmphibious,
        LavaFishType.SNAIL,
    ) { ItemLavaFish(SMALL_FISH_RAW) }

    @JvmField
    val YETI_CRAB = this.registerFish(
        "yeti_crab",
        EntityAmphibious::class.java,
        ::EntityAmphibious,
        LavaFishType.CRAB,
    ) { ItemLavaFish(SMALL_FISH_RAW) }

    @JvmField
    val LAVA_LAMPREY = this.registerFish(
        "lava_lamprey",
        EntityCommonFish::class.java,
        ::EntityCommonFish,
        LavaFishType.EEL,
    ) { ItemLavaFish() }

    @JvmField
    val SPICY_FISH_FILLET = this.register("spicy_fish_fillet") { ItemSpicyFishFillet() }

    @JvmField
    val FISH_PASTE = this.register("fish_paste") {
        ItemSimpleFood {
            nutrition(7)
            saturationMod(0.5f)
        }
    }

    @JvmField
    val PROMETHIUM_HELMET = this.register("promethium_helmet") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1")
    }

    @JvmField
    val PROMETHIUM_CHESTPLATE = this.register("promethium_chestplate") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.CHESTPLATE).setArmorTexture("promethium_layer_1")
    }

    @JvmField
    val PROMETHIUM_LEGGINGS = this.register("promethium_leggings") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2")
    }

    @JvmField
    val PROMETHIUM_BOOTS = this.register("promethium_boots") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1")
    }

    @JvmField
    val IRON_SLINGSHOT = this.register("iron_slingshot") { ItemSlingshot(Tiers.IRON, Properties()) }

    @JvmField
    val NEPTUNIUM_SLINGSHOT = this.register("neptunium_slingshot") { ItemNeptuniumSlingshot() }

    @JvmField
    val PROMETHIUM_SLINGSHOT = this.register("promethium_slingshot") { ItemPromethiumSlingshot() }

    @JvmField
    val STONE_BULLET = this.register("stone_bullet") {
        ItemBullet(Tiers.STONE, Properties()) { ModEntityTypes.STONE_BULLET.get() }
    }

    @JvmField
    val IRON_BULLET = this.register("iron_bullet") {
        ItemBullet(Tiers.IRON, Properties()) { ModEntityTypes.IRON_BULLET.get() }
    }

    @JvmField
    val NEPTUNIUM_BULLET = this.register("neptunium_bullet") {
        ItemBullet(AquacultureAPI.MATS.NEPTUNIUM, Properties()) { ModEntityTypes.NEPTUNIUM_BULLET.get() }
    }

    @JvmField
    val PROMETHIUM_BULLET = this.register("promethium_bullet") { ItemPromethiumBullet() }

    @JvmField
    val HYDROTHERMAL_HOOK = this.registerHook(
        "hydrothermal_hook",
        HookBuilder("hydrothermal")
            .setModID(BuildConstants.MOD_ID)
            .setFluid(FluidTags.WATER)
            .setFluid(FluidTags.LAVA)
            .setColor(ChatFormatting.AQUA)
            .build(),
    )

    @JvmField
    val PROMETHIUM_INGOT = this.register("promethium_ingot") { Item(Properties().fireResistant()) }

    @JvmField
    val PROMETHIUM_NUGGET = this.register("promethium_nugget") { Item(Properties().fireResistant()) }

    @JvmField
    val PROMETHIUM_BLOCK = this.register("promethium_block") {
        BlockItem(ModBlocks.PROMETHIUM_BLOCK.get(), Properties().fireResistant())
    }

    @JvmField
    val PROMETHEUS_BOUNTY = this.register("prometheus_bounty") {
        BlockItemWithoutLevelRenderer(ModBlocks.PROMETHEUS_BOUNTY.get(), Properties().fireResistant()) {
            BlockEntityPrometheusBounty(BlockPos.ZERO, ModBlocks.PROMETHEUS_BOUNTY.get().defaultBlockState())
        }
    }

    private fun registerHook(name: String, hook: Hook): RegistryObject<Item> {
        return this.register(name) { HookItem(hook) as Item }
            .also { Hook.HOOKS[hook.name] = it }
    }

    private fun <T : EntityLavaFish> registerFish(
        name: String,
        entityClass: Class<T>,
        fishConstructor: (EntityType<T>, Level, LavaFishType) -> T,
        fishType: LavaFishType,
        itemSupplier: () -> ItemLavaFish,
    ): RegistryObject<ItemLavaFish> {
        val fish = ModEntityTypes.register(name, entityClass) {
            EntityType.Builder.of(
                { type: EntityType<T>, level: Level -> fishConstructor(type, level, fishType) },
                MobCategory.WATER_AMBIENT,
            ).sized(fishType.width, fishType.height).build("${BuildConstants.MOD_ID}:$name")
        }

        this.register(name + "_bucket") {
            MobBucketItem(
                { fish.get() },
                { Fluids.LAVA },
                { SoundEvents.BUCKET_EMPTY_FISH },
                Properties().stacksTo(1),
            )
        }

        return this.register(name, itemSupplier)
    }

    fun onClientSetup(event: FMLClientSetupEvent) {
        event.enqueueWork {
            this.getEntriesIsInstance<FishingRodItem>().forEach(ClientHandler::registerFishingRodModelProperties)
        }
    }
}
