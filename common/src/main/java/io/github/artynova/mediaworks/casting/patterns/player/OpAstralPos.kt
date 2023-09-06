package io.github.artynova.mediaworks.casting.patterns.player

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.iota.NullIota
import io.github.artynova.mediaworks.MediaworksAbstractions
import net.minecraft.util.math.Vec3d

class OpAstralPos : ConstMediaAction {
    override val argc: Int = 0
    override val isGreat: Boolean = true

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val coords: Vec3d? = MediaworksAbstractions.getAstralPosition(ctx.caster)?.coordinates
        if (coords == null) return listOf(NullIota())
        return Vec3d(coords.x, coords.y + ctx.caster.standingEyeHeight, coords.z).asActionResult
    }
}