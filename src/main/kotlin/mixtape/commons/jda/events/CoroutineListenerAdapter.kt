package mixtape.commons.jda.events

import net.dv8tion.jda.api.events.*
import net.dv8tion.jda.api.events.application.ApplicationCommandCreateEvent
import net.dv8tion.jda.api.events.application.ApplicationCommandDeleteEvent
import net.dv8tion.jda.api.events.application.ApplicationCommandUpdateEvent
import net.dv8tion.jda.api.events.application.GenericApplicationCommandEvent
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent
import net.dv8tion.jda.api.events.channel.category.GenericCategoryEvent
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent
import net.dv8tion.jda.api.events.channel.category.update.GenericCategoryUpdateEvent
import net.dv8tion.jda.api.events.channel.store.GenericStoreChannelEvent
import net.dv8tion.jda.api.events.channel.store.StoreChannelCreateEvent
import net.dv8tion.jda.api.events.channel.store.StoreChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.store.update.GenericStoreChannelUpdateEvent
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdateNameEvent
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdatePositionEvent
import net.dv8tion.jda.api.events.channel.text.GenericTextChannelEvent
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.text.update.*
import net.dv8tion.jda.api.events.channel.voice.GenericVoiceChannelEvent
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.voice.update.*
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent
import net.dv8tion.jda.api.events.emote.GenericEmoteEvent
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent
import net.dv8tion.jda.api.events.emote.update.GenericEmoteUpdateEvent
import net.dv8tion.jda.api.events.guild.*
import net.dv8tion.jda.api.events.guild.invite.GenericGuildInviteEvent
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent
import net.dv8tion.jda.api.events.guild.member.*
import net.dv8tion.jda.api.events.guild.member.update.GenericGuildMemberUpdateEvent
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent
import net.dv8tion.jda.api.events.guild.override.GenericPermissionOverrideEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent
import net.dv8tion.jda.api.events.guild.update.*
import net.dv8tion.jda.api.events.guild.voice.*
import net.dv8tion.jda.api.events.http.HttpRequestEvent
import net.dv8tion.jda.api.events.interaction.*
import net.dv8tion.jda.api.events.message.*
import net.dv8tion.jda.api.events.message.guild.*
import net.dv8tion.jda.api.events.message.guild.react.*
import net.dv8tion.jda.api.events.message.priv.*
import net.dv8tion.jda.api.events.message.priv.react.GenericPrivateMessageReactionEvent
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionAddEvent
import net.dv8tion.jda.api.events.message.priv.react.PrivateMessageReactionRemoveEvent
import net.dv8tion.jda.api.events.message.react.*
import net.dv8tion.jda.api.events.role.GenericRoleEvent
import net.dv8tion.jda.api.events.role.RoleCreateEvent
import net.dv8tion.jda.api.events.role.RoleDeleteEvent
import net.dv8tion.jda.api.events.role.update.*
import net.dv8tion.jda.api.events.self.*
import net.dv8tion.jda.api.events.stage.GenericStageInstanceEvent
import net.dv8tion.jda.api.events.stage.StageInstanceCreateEvent
import net.dv8tion.jda.api.events.stage.StageInstanceDeleteEvent
import net.dv8tion.jda.api.events.stage.update.GenericStageInstanceUpdateEvent
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdatePrivacyLevelEvent
import net.dv8tion.jda.api.events.stage.update.StageInstanceUpdateTopicEvent
import net.dv8tion.jda.api.events.user.GenericUserEvent
import net.dv8tion.jda.api.events.user.UserActivityEndEvent
import net.dv8tion.jda.api.events.user.UserActivityStartEvent
import net.dv8tion.jda.api.events.user.UserTypingEvent
import net.dv8tion.jda.api.events.user.update.*
import net.dv8tion.jda.internal.utils.ClassWalker
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.annotation.Nonnull

@Suppress("UNUSED")
open class CoroutineListenerAdapter : CoroutineListener() {
    open suspend fun onGenericEvent(@Nonnull event: GenericEvent) = Unit
    open suspend fun onGenericUpdate(@Nonnull event: UpdateEvent<*, *>) = Unit
    open suspend fun onRawGateway(@Nonnull event: RawGatewayEvent) = Unit
    open suspend fun onGatewayPing(@Nonnull event: GatewayPingEvent) = Unit

    //JDA Events
    open suspend fun onReady(@Nonnull event: ReadyEvent) = Unit
    open suspend fun onResumed(@Nonnull event: ResumedEvent) = Unit
    open suspend fun onReconnected(@Nonnull event: ReconnectedEvent) = Unit
    open suspend fun onDisconnect(@Nonnull event: DisconnectEvent) = Unit
    open suspend fun onShutdown(@Nonnull event: ShutdownEvent) = Unit
    open suspend fun onStatusChange(@Nonnull event: StatusChangeEvent) = Unit
    open suspend fun onException(@Nonnull event: ExceptionEvent) = Unit

    //Interaction Events
    open suspend fun onSlashCommand(@Nonnull event: SlashCommandEvent) = Unit
    open suspend fun onButtonClick(@Nonnull event: ButtonClickEvent) = Unit
    open suspend fun onSelectionMenu(@Nonnull event: SelectionMenuEvent) = Unit

    //Application Events
    open suspend fun onApplicationCommandUpdate(@Nonnull event: ApplicationCommandUpdateEvent) = Unit
    open suspend fun onApplicationCommandDelete(@Nonnull event: ApplicationCommandDeleteEvent) = Unit
    open suspend fun onApplicationCommandCreate(@Nonnull event: ApplicationCommandCreateEvent) = Unit

    //User Events
    open suspend fun onUserUpdateName(@Nonnull event: UserUpdateNameEvent) = Unit
    open suspend fun onUserUpdateDiscriminator(@Nonnull event: UserUpdateDiscriminatorEvent) = Unit
    open suspend fun onUserUpdateAvatar(@Nonnull event: UserUpdateAvatarEvent) = Unit
    open suspend fun onUserUpdateOnlineStatus(@Nonnull event: UserUpdateOnlineStatusEvent) = Unit
    open suspend fun onUserUpdateActivityOrder(@Nonnull event: UserUpdateActivityOrderEvent) = Unit
    open suspend fun onUserUpdateFlags(@Nonnull event: UserUpdateFlagsEvent) = Unit
    open suspend fun onUserTyping(@Nonnull event: UserTypingEvent) = Unit
    open suspend fun onUserActivityStart(@Nonnull event: UserActivityStartEvent) = Unit
    open suspend fun onUserActivityEnd(@Nonnull event: UserActivityEndEvent) = Unit
    open suspend fun onUserUpdateActivities(@Nonnull event: UserUpdateActivitiesEvent) = Unit

    //Self Events. Fires only in relation to the currently logged in account.
    open suspend fun onSelfUpdateAvatar(@Nonnull event: SelfUpdateAvatarEvent) = Unit
    open suspend fun onSelfUpdateMFA(@Nonnull event: SelfUpdateMFAEvent) = Unit
    open suspend fun onSelfUpdateName(@Nonnull event: SelfUpdateNameEvent) = Unit
    open suspend fun onSelfUpdateVerified(@Nonnull event: SelfUpdateVerifiedEvent) = Unit

    //Message Events
    //Guild (TextChannel) Message Events
    open suspend fun onGuildMessageReceived(@Nonnull event: GuildMessageReceivedEvent) = Unit
    open suspend fun onGuildMessageUpdate(@Nonnull event: GuildMessageUpdateEvent) = Unit
    open suspend fun onGuildMessageDelete(@Nonnull event: GuildMessageDeleteEvent) = Unit
    open suspend fun onGuildMessageEmbed(@Nonnull event: GuildMessageEmbedEvent) = Unit
    open suspend fun onGuildMessageReactionAdd(@Nonnull event: GuildMessageReactionAddEvent) = Unit
    open suspend fun onGuildMessageReactionRemove(@Nonnull event: GuildMessageReactionRemoveEvent) = Unit
    open suspend fun onGuildMessageReactionRemoveAll(@Nonnull event: GuildMessageReactionRemoveAllEvent) = Unit
    open suspend fun onGuildMessageReactionRemoveEmote(@Nonnull event: GuildMessageReactionRemoveEmoteEvent) = Unit

    //Private Message Events
    open suspend fun onPrivateMessageReceived(@Nonnull event: PrivateMessageReceivedEvent) = Unit
    open suspend fun onPrivateMessageUpdate(@Nonnull event: PrivateMessageUpdateEvent) = Unit
    open suspend fun onPrivateMessageDelete(@Nonnull event: PrivateMessageDeleteEvent) = Unit
    open suspend fun onPrivateMessageEmbed(@Nonnull event: PrivateMessageEmbedEvent) = Unit
    open suspend fun onPrivateMessageReactionAdd(@Nonnull event: PrivateMessageReactionAddEvent) = Unit
    open suspend fun onPrivateMessageReactionRemove(@Nonnull event: PrivateMessageReactionRemoveEvent) = Unit

    //Combined Message Events (Combines Guild and Private message into 1 event)
    open suspend fun onMessageReceived(@Nonnull event: MessageReceivedEvent) = Unit
    open suspend fun onMessageUpdate(@Nonnull event: MessageUpdateEvent) = Unit
    open suspend fun onMessageDelete(@Nonnull event: MessageDeleteEvent) = Unit
    open suspend fun onMessageBulkDelete(@Nonnull event: MessageBulkDeleteEvent) = Unit
    open suspend fun onMessageEmbed(@Nonnull event: MessageEmbedEvent) = Unit
    open suspend fun onMessageReactionAdd(@Nonnull event: MessageReactionAddEvent) = Unit
    open suspend fun onMessageReactionRemove(@Nonnull event: MessageReactionRemoveEvent) = Unit
    open suspend fun onMessageReactionRemoveAll(@Nonnull event: MessageReactionRemoveAllEvent) = Unit
    open suspend fun onMessageReactionRemoveEmote(@Nonnull event: MessageReactionRemoveEmoteEvent) = Unit

    //PermissionOverride Events
    open suspend fun onPermissionOverrideDelete(@Nonnull event: PermissionOverrideDeleteEvent) = Unit
    open suspend fun onPermissionOverrideUpdate(@Nonnull event: PermissionOverrideUpdateEvent) = Unit
    open suspend fun onPermissionOverrideCreate(@Nonnull event: PermissionOverrideCreateEvent) = Unit

    //StoreChannel Events
    open suspend fun onStoreChannelDelete(@Nonnull event: StoreChannelDeleteEvent) = Unit
    open suspend fun onStoreChannelUpdateName(@Nonnull event: StoreChannelUpdateNameEvent) = Unit
    open suspend fun onStoreChannelUpdatePosition(@Nonnull event: StoreChannelUpdatePositionEvent) = Unit
    open suspend fun onStoreChannelCreate(@Nonnull event: StoreChannelCreateEvent) = Unit

    //TextChannel Events
    open suspend fun onTextChannelDelete(@Nonnull event: TextChannelDeleteEvent) = Unit
    open suspend fun onTextChannelUpdateName(@Nonnull event: TextChannelUpdateNameEvent) = Unit
    open suspend fun onTextChannelUpdateTopic(@Nonnull event: TextChannelUpdateTopicEvent) = Unit
    open suspend fun onTextChannelUpdatePosition(@Nonnull event: TextChannelUpdatePositionEvent) = Unit
    open suspend fun onTextChannelUpdateNSFW(@Nonnull event: TextChannelUpdateNSFWEvent) = Unit
    open suspend fun onTextChannelUpdateParent(@Nonnull event: TextChannelUpdateParentEvent) = Unit
    open suspend fun onTextChannelUpdateSlowmode(@Nonnull event: TextChannelUpdateSlowmodeEvent) = Unit
    open suspend fun onTextChannelUpdateNews(@Nonnull event: TextChannelUpdateNewsEvent) = Unit
    open suspend fun onTextChannelCreate(@Nonnull event: TextChannelCreateEvent) = Unit

    //VoiceChannel Events
    open suspend fun onVoiceChannelDelete(@Nonnull event: VoiceChannelDeleteEvent) = Unit
    open suspend fun onVoiceChannelUpdateName(@Nonnull event: VoiceChannelUpdateNameEvent) = Unit
    open suspend fun onVoiceChannelUpdatePosition(@Nonnull event: VoiceChannelUpdatePositionEvent) = Unit
    open suspend fun onVoiceChannelUpdateUserLimit(@Nonnull event: VoiceChannelUpdateUserLimitEvent) = Unit
    open suspend fun onVoiceChannelUpdateBitrate(@Nonnull event: VoiceChannelUpdateBitrateEvent) = Unit
    open suspend fun onVoiceChannelUpdateParent(@Nonnull event: VoiceChannelUpdateParentEvent) = Unit
    open suspend fun onVoiceChannelUpdateRegion(@Nonnull event: VoiceChannelUpdateRegionEvent) = Unit
    open suspend fun onVoiceChannelCreate(@Nonnull event: VoiceChannelCreateEvent) = Unit

    //Category Events
    open suspend fun onCategoryDelete(@Nonnull event: CategoryDeleteEvent) = Unit
    open suspend fun onCategoryUpdateName(@Nonnull event: CategoryUpdateNameEvent) = Unit
    open suspend fun onCategoryUpdatePosition(@Nonnull event: CategoryUpdatePositionEvent) = Unit
    open suspend fun onCategoryCreate(@Nonnull event: CategoryCreateEvent) = Unit

    //StageInstance Event
    open suspend fun onStageInstanceDelete(@Nonnull event: StageInstanceDeleteEvent) = Unit
    open suspend fun onStageInstanceUpdateTopic(@Nonnull event: StageInstanceUpdateTopicEvent) = Unit
    open suspend fun onStageInstanceUpdatePrivacyLevel(@Nonnull event: StageInstanceUpdatePrivacyLevelEvent) = Unit
    open suspend fun onStageInstanceCreate(@Nonnull event: StageInstanceCreateEvent) = Unit

    //Guild Events
    open suspend fun onGuildReady(@Nonnull event: GuildReadyEvent) = Unit
    open suspend fun onGuildTimeout(@Nonnull event: GuildTimeoutEvent) = Unit
    open suspend fun onGuildJoin(@Nonnull event: GuildJoinEvent) = Unit
    open suspend fun onGuildLeave(@Nonnull event: GuildLeaveEvent) = Unit
    open suspend fun onGuildAvailable(@Nonnull event: GuildAvailableEvent) = Unit
    open suspend fun onGuildUnavailable(@Nonnull event: GuildUnavailableEvent) = Unit
    open suspend fun onUnavailableGuildJoined(@Nonnull event: UnavailableGuildJoinedEvent) = Unit
    open suspend fun onUnavailableGuildLeave(@Nonnull event: UnavailableGuildLeaveEvent) = Unit
    open suspend fun onGuildBan(@Nonnull event: GuildBanEvent) = Unit
    open suspend fun onGuildUnban(@Nonnull event: GuildUnbanEvent) = Unit
    open suspend fun onGuildMemberRemove(@Nonnull event: GuildMemberRemoveEvent) = Unit

    //Guild Update Events
    open suspend fun onGuildUpdateAfkChannel(@Nonnull event: GuildUpdateAfkChannelEvent) = Unit
    open suspend fun onGuildUpdateSystemChannel(@Nonnull event: GuildUpdateSystemChannelEvent) = Unit
    open suspend fun onGuildUpdateRulesChannel(@Nonnull event: GuildUpdateRulesChannelEvent) = Unit
    open suspend fun onGuildUpdateCommunityUpdatesChannel(@Nonnull event: GuildUpdateCommunityUpdatesChannelEvent) = Unit
    open suspend fun onGuildUpdateAfkTimeout(@Nonnull event: GuildUpdateAfkTimeoutEvent) = Unit
    open suspend fun onGuildUpdateExplicitContentLevel(@Nonnull event: GuildUpdateExplicitContentLevelEvent) = Unit
    open suspend fun onGuildUpdateIcon(@Nonnull event: GuildUpdateIconEvent) = Unit
    open suspend fun onGuildUpdateMFALevel(@Nonnull event: GuildUpdateMFALevelEvent) = Unit
    open suspend fun onGuildUpdateName(@Nonnull event: GuildUpdateNameEvent) = Unit
    open suspend fun onGuildUpdateNotificationLevel(@Nonnull event: GuildUpdateNotificationLevelEvent) = Unit
    open suspend fun onGuildUpdateOwner(@Nonnull event: GuildUpdateOwnerEvent) = Unit

    open suspend fun onGuildUpdateSplash(@Nonnull event: GuildUpdateSplashEvent) = Unit
    open suspend fun onGuildUpdateVerificationLevel(@Nonnull event: GuildUpdateVerificationLevelEvent) = Unit
    open suspend fun onGuildUpdateLocale(@Nonnull event: GuildUpdateLocaleEvent) = Unit
    open suspend fun onGuildUpdateFeatures(@Nonnull event: GuildUpdateFeaturesEvent) = Unit
    open suspend fun onGuildUpdateVanityCode(@Nonnull event: GuildUpdateVanityCodeEvent) = Unit
    open suspend fun onGuildUpdateBanner(@Nonnull event: GuildUpdateBannerEvent) = Unit
    open suspend fun onGuildUpdateDescription(@Nonnull event: GuildUpdateDescriptionEvent) = Unit
    open suspend fun onGuildUpdateBoostTier(@Nonnull event: GuildUpdateBoostTierEvent) = Unit
    open suspend fun onGuildUpdateBoostCount(@Nonnull event: GuildUpdateBoostCountEvent) = Unit
    open suspend fun onGuildUpdateMaxMembers(@Nonnull event: GuildUpdateMaxMembersEvent) = Unit
    open suspend fun onGuildUpdateMaxPresences(@Nonnull event: GuildUpdateMaxPresencesEvent) = Unit
    open suspend fun onGuildUpdateNSFWLevel(@Nonnull event: GuildUpdateNSFWLevelEvent) = Unit

    //Guild Invite Events
    open suspend fun onGuildInviteCreate(@Nonnull event: GuildInviteCreateEvent) = Unit
    open suspend fun onGuildInviteDelete(@Nonnull event: GuildInviteDeleteEvent) = Unit

    //Guild Member Events
    open suspend fun onGuildMemberJoin(@Nonnull event: GuildMemberJoinEvent) = Unit
    open suspend fun onGuildMemberRoleAdd(@Nonnull event: GuildMemberRoleAddEvent) = Unit
    open suspend fun onGuildMemberRoleRemove(@Nonnull event: GuildMemberRoleRemoveEvent) = Unit

    //Guild Member Update Events
    open suspend fun onGuildMemberUpdate(@Nonnull event: GuildMemberUpdateEvent) = Unit
    open suspend fun onGuildMemberUpdateNickname(@Nonnull event: GuildMemberUpdateNicknameEvent) = Unit
    open suspend fun onGuildMemberUpdateBoostTime(@Nonnull event: GuildMemberUpdateBoostTimeEvent) = Unit
    open suspend fun onGuildMemberUpdatePending(@Nonnull event: GuildMemberUpdatePendingEvent) = Unit

    //Guild Voice Events
    open suspend fun onGuildVoiceUpdate(@Nonnull event: GuildVoiceUpdateEvent) = Unit
    open suspend fun onGuildVoiceJoin(@Nonnull event: GuildVoiceJoinEvent) = Unit
    open suspend fun onGuildVoiceMove(@Nonnull event: GuildVoiceMoveEvent) = Unit
    open suspend fun onGuildVoiceLeave(@Nonnull event: GuildVoiceLeaveEvent) = Unit
    open suspend fun onGuildVoiceMute(@Nonnull event: GuildVoiceMuteEvent) = Unit
    open suspend fun onGuildVoiceDeafen(@Nonnull event: GuildVoiceDeafenEvent) = Unit
    open suspend fun onGuildVoiceGuildMute(@Nonnull event: GuildVoiceGuildMuteEvent) = Unit
    open suspend fun onGuildVoiceGuildDeafen(@Nonnull event: GuildVoiceGuildDeafenEvent) = Unit
    open suspend fun onGuildVoiceSelfMute(@Nonnull event: GuildVoiceSelfMuteEvent) = Unit
    open suspend fun onGuildVoiceSelfDeafen(@Nonnull event: GuildVoiceSelfDeafenEvent) = Unit
    open suspend fun onGuildVoiceSuppress(@Nonnull event: GuildVoiceSuppressEvent) = Unit
    open suspend fun onGuildVoiceStream(@Nonnull event: GuildVoiceStreamEvent) = Unit
    open suspend fun onGuildVoiceVideo(@Nonnull event: GuildVoiceVideoEvent) = Unit
    open suspend fun onGuildVoiceRequestToSpeak(@Nonnull event: GuildVoiceRequestToSpeakEvent) = Unit

    //Role events
    open suspend fun onRoleCreate(@Nonnull event: RoleCreateEvent) = Unit
    open suspend fun onRoleDelete(@Nonnull event: RoleDeleteEvent) = Unit

    //Role Update Events
    open suspend fun onRoleUpdateColor(@Nonnull event: RoleUpdateColorEvent) = Unit
    open suspend fun onRoleUpdateHoisted(@Nonnull event: RoleUpdateHoistedEvent) = Unit
    open suspend fun onRoleUpdateMentionable(@Nonnull event: RoleUpdateMentionableEvent) = Unit
    open suspend fun onRoleUpdateName(@Nonnull event: RoleUpdateNameEvent) = Unit
    open suspend fun onRoleUpdatePermissions(@Nonnull event: RoleUpdatePermissionsEvent) = Unit
    open suspend fun onRoleUpdatePosition(@Nonnull event: RoleUpdatePositionEvent) = Unit

    //Emote Events
    open suspend fun onEmoteAdded(@Nonnull event: EmoteAddedEvent) = Unit
    open suspend fun onEmoteRemoved(@Nonnull event: EmoteRemovedEvent) = Unit

    //Emote Update Events
    open suspend fun onEmoteUpdateName(@Nonnull event: EmoteUpdateNameEvent) = Unit
    open suspend fun onEmoteUpdateRoles(@Nonnull event: EmoteUpdateRolesEvent) = Unit

    // Debug Events
    open suspend fun onHttpRequest(@Nonnull event: HttpRequestEvent) = Unit

    //Generic Events
    open suspend fun onGenericApplicationCommand(@Nonnull event: GenericApplicationCommandEvent) = Unit
    open suspend fun onGenericInteractionCreate(@Nonnull event: GenericInteractionCreateEvent) = Unit
    open suspend fun onGenericComponentInteractionCreate(@Nonnull event: GenericComponentInteractionCreateEvent) = Unit
    open suspend fun onGenericMessage(@Nonnull event: GenericMessageEvent) = Unit
    open suspend fun onGenericMessageReaction(@Nonnull event: GenericMessageReactionEvent) = Unit
    open suspend fun onGenericGuildMessage(@Nonnull event: GenericGuildMessageEvent) = Unit
    open suspend fun onGenericGuildMessageReaction(@Nonnull event: GenericGuildMessageReactionEvent) = Unit
    open suspend fun onGenericPrivateMessage(@Nonnull event: GenericPrivateMessageEvent) = Unit
    open suspend fun onGenericPrivateMessageReaction(@Nonnull event: GenericPrivateMessageReactionEvent) = Unit
    open suspend fun onGenericUser(@Nonnull event: GenericUserEvent) = Unit
    open suspend fun onGenericUserPresence(@Nonnull event: GenericUserPresenceEvent) = Unit
    open suspend fun onGenericSelfUpdate(@Nonnull event: GenericSelfUpdateEvent<*>) = Unit
    open suspend fun onGenericStoreChannel(@Nonnull event: GenericStoreChannelEvent) = Unit
    open suspend fun onGenericStoreChannelUpdate(@Nonnull event: GenericStoreChannelUpdateEvent<*>) = Unit
    open suspend fun onGenericTextChannel(@Nonnull event: GenericTextChannelEvent) = Unit
    open suspend fun onGenericTextChannelUpdate(@Nonnull event: GenericTextChannelUpdateEvent<*>) = Unit
    open suspend fun onGenericVoiceChannel(@Nonnull event: GenericVoiceChannelEvent) = Unit
    open suspend fun onGenericVoiceChannelUpdate(@Nonnull event: GenericVoiceChannelUpdateEvent<*>) = Unit
    open suspend fun onGenericCategory(@Nonnull event: GenericCategoryEvent) = Unit
    open suspend fun onGenericCategoryUpdate(@Nonnull event: GenericCategoryUpdateEvent<*>) = Unit
    open suspend fun onGenericStageInstance(@Nonnull event: GenericStageInstanceEvent) = Unit
    open suspend fun onGenericStageInstanceUpdate(@Nonnull event: GenericStageInstanceUpdateEvent<*>) = Unit
    open suspend fun onGenericGuild(@Nonnull event: GenericGuildEvent) = Unit
    open suspend fun onGenericGuildUpdate(@Nonnull event: GenericGuildUpdateEvent<*>) = Unit
    open suspend fun onGenericGuildInvite(@Nonnull event: GenericGuildInviteEvent) = Unit
    open suspend fun onGenericGuildMember(@Nonnull event: GenericGuildMemberEvent) = Unit
    open suspend fun onGenericGuildMemberUpdate(@Nonnull event: GenericGuildMemberUpdateEvent<*>) = Unit
    open suspend fun onGenericGuildVoice(@Nonnull event: GenericGuildVoiceEvent) = Unit
    open suspend fun onGenericRole(@Nonnull event: GenericRoleEvent) = Unit
    open suspend fun onGenericRoleUpdate(@Nonnull event: GenericRoleUpdateEvent<*>) = Unit
    open suspend fun onGenericEmote(@Nonnull event: GenericEmoteEvent) = Unit
    open suspend fun onGenericEmoteUpdate(@Nonnull event: GenericEmoteUpdateEvent<*>) = Unit
    open suspend fun onGenericPermissionOverride(@Nonnull event: GenericPermissionOverrideEvent) = Unit

    private fun findMethod(clazz: Class<*>): MethodHandle? {
        val type = MethodType.methodType(Void.TYPE, clazz)
        
        try {
            val methodName = "on${clazz.simpleName.removeSuffix("Event")}"
            return lookup.findVirtual(CoroutineListenerAdapter::class.java, methodName, type)
        } catch (ignored: NoSuchMethodException) {
            // this means this is probably a custom event!
        } catch (ignored: IllegalAccessException) {
        }
        
        return null
    }

    override suspend fun handleEvent(event: GenericEvent) {
        onGenericEvent(event)
        if (event is UpdateEvent<*, *>) {
            onGenericUpdate(event)
        }

        for (clazz in ClassWalker.range(event.javaClass, GenericEvent::class.java)) {
            if (unresolved.contains(clazz)) {
                continue
            }

            val mh = methods.computeIfAbsent(clazz) { findMethod(it) }
                ?: return run { unresolved.add(clazz) }

            try {
                mh.invoke(this, event)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is RuntimeException -> throw throwable
                    is Error -> throw throwable
                    else -> throw IllegalStateException(throwable)
                }
            }
        }
    }

    companion object {
        private val lookup = MethodHandles.lookup()
        private val methods: ConcurrentMap<Class<*>, MethodHandle> = ConcurrentHashMap()
        private var unresolved: MutableSet<Class<*>> =
            hashSetOf(Object::class.java, Event::class.java, UpdateEvent::class.java, GenericEvent::class.java)
    }
}
