package commands

import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.entities.Cog
import mixtape.commons.extensions.reply
import mixtape.commons.text.PaddingDirection
import mixtape.commons.text.buildProlog

class Test : Cog {
    @Command
    fun test(ctx: Context) {
        ctx.reply {
            description = buildProlog(direction = PaddingDirection.End) {
                line("Latency", "42069 ms")
                line("Poop", "Shit")
            }
        }
    }
}
