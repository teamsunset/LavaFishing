package club.redux.sunset.lavafishing.registry

import club.redux.sunset.lavafishing.BuildConstants
import club.redux.sunset.lavafishing.item.ItemObsidianFishingRod
import club.redux.sunset.lavafishing.item.ItemPromethiumArmor
import club.redux.sunset.lavafishing.item.block.BlockItemWithoutLevelRenderer
import club.redux.sunset.lavafishing.item.bullet.ItemBullet
import club.redux.sunset.lavafishing.item.cuisine.ItemSpicyFishFillet
import club.redux.sunset.lavafishing.item.fishes.*
import club.redux.sunset.lavafishing.item.slingshot.ItemNeptuniumSlingshot
import club.redux.sunset.lavafishing.item.slingshot.ItemPromethiumSlingshot
import club.redux.sunset.lavafishing.misc.ModArmorMaterials
import club.redux.sunset.lavafishing.misc.ModTiers
import club.redux.sunset.lavafishing.util.UtilRegister
import club.redux.sunset.lavafishing.util.registerKt
import com.teammetallurgy.aquaculture.api.AquacultureAPI
import com.teammetallurgy.aquaculture.item.FishItem.SMALL_FISH_RAW
import com.teammetallurgy.aquaculture.item.SimpleItem
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.Tiers
import net.minecraftforge.registries.ForgeRegistries

object ModItems {
    @JvmField val REGISTER = UtilRegister.create(ForgeRegistries.ITEMS, BuildConstants.MOD_ID)

    @JvmField val OBSIDIAN_FISHING_ROD = REGISTER.registerKt("obsidian_fishing_rod") { ItemObsidianFishingRod() }

    // Fish
    @JvmField val FLAME_SQUAT_LOBSTER = REGISTER.registerKt("flame_squat_lobster") { ItemFlameSquatLobster() }
    @JvmField val OBSIDIAN_SWORD_FISH = REGISTER.registerKt("obsidian_sword_fish") { ItemObsidianSwordFish() }
    @JvmField val STEAM_FLYING_FISH = REGISTER.registerKt("steam_flying_fish") { ItemSteamFlyingFish() }
    @JvmField val AGNI_FISH = REGISTER.registerKt("agni_fish") { ItemAgniFish() }

    @JvmField val AROWANA_FISH = REGISTER.registerKt("arowana_fish") { ItemLavaFish() }
    @JvmField val QUARTZ_FISH = REGISTER.registerKt("quartz_fish") { ItemLavaFish() }
    @JvmField val SCALY_FOOT_SNAIL = REGISTER.registerKt("scaly_foot_snail") { ItemLavaFish(SMALL_FISH_RAW) }
    @JvmField val YETI_CRAB = REGISTER.registerKt("yeti_crab") { ItemLavaFish(SMALL_FISH_RAW) }
    @JvmField val LAVA_LAMPREY = REGISTER.registerKt("lava_lamprey") { ItemLavaFish() }


    // FISH_DATA
    // TODO
    fun registerFishData() {
        REGISTER.entries.map { it.get() }.filterIsInstance<ItemLavaFish>().forEach {
            AquacultureAPI.FISH_DATA.add(it, 100.0, 200.0, it.filletAmount)
        }
    }

    // Food
    val SPICY_FISH_FILLET = REGISTER.registerKt("spicy_fish_fillet") { ItemSpicyFishFillet() }

    // Armor
    @JvmField val PROMETHIUM_INGOT = REGISTER.registerKt("promethium_ingot") {
        SimpleItem(Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_NUGGET = REGISTER.registerKt("promethium_nugget") {
        SimpleItem(Properties().fireResistant())
    }
    @JvmField val PROMETHIUM_HELMET = REGISTER.registerKt("promethium_helmet") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.HELMET).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_CHESTPLATE = REGISTER.registerKt("promethium_chestplate") {
        ItemPromethiumArmor(
            ModArmorMaterials.PROMETHIUM,
            ArmorItem.Type.CHESTPLATE
        ).setArmorTexture("promethium_layer_1")
    }
    @JvmField val PROMETHIUM_LEGGINGS = REGISTER.registerKt("promethium_leggings") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.LEGGINGS).setArmorTexture("promethium_layer_2")
    }
    @JvmField val PROMETHIUM_BOOTS = REGISTER.registerKt("promethium_boots") {
        ItemPromethiumArmor(ModArmorMaterials.PROMETHIUM, ArmorItem.Type.BOOTS).setArmorTexture("promethium_layer_1")
    }

    // Slingshot
    @JvmField val NEPTUNIUM_SLINGSHOT = REGISTER.registerKt("neptunium_slingshot") {
        ItemNeptuniumSlingshot()
    }

    @JvmField val PROMETHIUM_SLINGSHOT = REGISTER.registerKt("promethium_slingshot") {
        ItemPromethiumSlingshot()
    }

    @JvmField val STONE_BULLET = REGISTER.registerKt("stone_bullet") {
        ItemBullet(Tiers.STONE, Properties()) { ModEntityTypes.STONE_BULLET.get() }
    }

    @JvmField val IRON_BULLET = REGISTER.registerKt("iron_bullet") {
        ItemBullet(Tiers.IRON, Properties()) { ModEntityTypes.IRON_BULLET.get() }
    }

    @JvmField val NEPTUNIUM_BULLET = REGISTER.registerKt("neptunium_bullet") {
        ItemBullet(AquacultureAPI.MATS.NEPTUNIUM, Properties()) { ModEntityTypes.NEPTUNIUM_BULLET.get() }
    }

    @JvmField val PROMETHIUM_BULLET = REGISTER.registerKt("promethium_bullet") {
        ItemBullet(ModTiers.PROMETHIUM, Properties()) { ModEntityTypes.PROMETHIUM_BULLET.get() }
    }

    // other
    @JvmField val PROMETHIUM_BLOCK = REGISTER.registerKt("promethium_block") {
        BlockItem(ModBlocks.PROMETHIUM_BLOCK.get(), Properties().fireResistant())
    }

    @JvmField val PROMETHEUS_BOUNTY = REGISTER.registerKt("prometheus_bounty") {
        BlockItemWithoutLevelRenderer(ModBlocks.PROMETHEUS_BOUNTY.get(), Properties().fireResistant())
    }
}