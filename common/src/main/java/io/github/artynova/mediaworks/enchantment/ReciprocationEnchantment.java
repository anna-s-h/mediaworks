package io.github.artynova.mediaworks.enchantment;

import at.petrak.hexcasting.api.addldata.ADHexHolder;
import at.petrak.hexcasting.api.spell.casting.CastingContext;
import at.petrak.hexcasting.api.spell.casting.CastingHarness;
import at.petrak.hexcasting.api.spell.iota.Iota;
import at.petrak.hexcasting.xplat.IXplatAbstractions;
import io.github.artynova.mediaworks.api.enchantment.CloakEnchantment;
import io.github.artynova.mediaworks.casting.ExtendedCastingContext;
import io.github.artynova.mediaworks.item.MediaworksItems;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;

import java.util.List;

public class ReciprocationEnchantment extends CloakEnchantment {
    public ReciprocationEnchantment() {
        super(Rarity.UNCOMMON);
    }

    public static void processPlayerHurt(PlayerEntity maybeUser) {
        if (!(maybeUser instanceof ServerPlayerEntity player)) return;

        ItemStack stack = player.getEquippedStack(EquipmentSlot.HEAD);
        if (!stack.isOf(MediaworksItems.MAGIC_CLOAK.get())) return;

        int level = EnchantmentHelper.getLevel(MediaworksEnchantments.RECIPROCATION.get(), stack);
        if (level <= 0) return;

        ADHexHolder holder = IXplatAbstractions.INSTANCE.findHexHolder(stack);
        assert holder != null;
        List<Iota> hex = holder.getHex(player.getWorld());
        if (hex == null) return;

        CastingContext context = new CastingContext(player, Hand.OFF_HAND, CastingContext.CastSource.PACKAGED_HEX);
        ExtendedCastingContext extended = (ExtendedCastingContext) (Object) context;
        extended.mediaworks$setForcedCastingStack(stack);
        CastingHarness harness = new CastingHarness(context);
        harness.executeIotas(hex, player.getWorld());

        extended.mediaworks$setForcedCastingStack(null);
        return;
    }

    public int getMinPower(int level) {
        return 1;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 40;
    }
}
