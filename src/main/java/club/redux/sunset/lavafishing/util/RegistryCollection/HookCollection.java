package club.redux.sunset.lavafishing.util.RegistryCollection;

import club.redux.sunset.lavafishing.util.Reference;
import com.teammetallurgy.aquaculture.api.fishing.Hook;
import net.minecraft.ChatFormatting;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class HookCollection
{

    public static final Hook OBSIDIAN = new Hook.HookBuilder("obsidian").setColor(ChatFormatting.DARK_PURPLE).setFluid(FluidTags.LAVA).build();
    public static final Hook DOUBLE_OBSIDIAN = new Hook.HookBuilder("double_obsidian").setFluid(FluidTags.LAVA).setDoubleCatchChance(0.15).build();
    public static final Hook GLOWSTONE = new Hook.HookBuilder("glowstone").setColor(ChatFormatting.YELLOW).setFluid(FluidTags.LAVA).setLuckModifier(1).build();
    public static final Hook QUARTZ = new Hook.HookBuilder("quartz").setFluid(FluidTags.LAVA).setDurabilityChance(0.30).build();
    public static final Hook SOUL_SAND = new Hook.HookBuilder("soul_sand").setColor(ChatFormatting.DARK_GRAY).setFluid(FluidTags.LAVA).setCatchableWindow(40, 70).build();
    //    public static final Hook OBSIDIAN_NOTE = new Hook.HookBuilder("obsidian_note").setColor(ChatFormatting.LIGHT_PURPLE).setFluid(FluidTags.LAVA).setCatchSound(SoundEvents.LAVA_EXTINGUISH).build();
    public static final Hook OBSIDIAN_NOTE = new Hook.HookBuilder("obsidian_note").setColor(ChatFormatting.LIGHT_PURPLE).setFluid(FluidTags.LAVA).build();

}
