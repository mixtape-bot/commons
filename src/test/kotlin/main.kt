import me.devoxin.flight.api.events.Event
import me.devoxin.flight.internal.utils.Flight
import me.devoxin.flight.internal.utils.on
import mixtape.commons.extensions.intents
import mixtape.commons.extensions.JDA
import mixtape.commons.extensions.on
import mixtape.commons.extensions.useFlowingEventManager
import mu.KotlinLogging
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.requests.GatewayIntent

val log = KotlinLogging.logger {  }

fun main(args: Array<out String>) {
    val flight = Flight {
        prefixes += "!"

//        registerDefaultResolvers()
    }

    flight.on<Event> {
        println(this)
    }

    val jda = JDA(args.firstOrNull() ?: error("Provide a bot token idiot")) {
        intents += GatewayIntent.GUILD_MESSAGES

        addEventListeners(flight)
        useFlowingEventManager()
    }

    jda.on<ReadyEvent> {
        log.info { "Ready as ${jda.selfUser.asTag}" }
    }

    flight.commands.register("commands")
}
