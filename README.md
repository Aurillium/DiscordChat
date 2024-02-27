# DiscordChat
A Spigot plugin designed to allow your Minecraft chat to show up in Discord.


## Config

Your configuration file will look like this:
```yml
# If set to false, no messages will be sent to Discord
master: true
# A list of user IDs of players who have opted out
opt_out_chat:
-
# A list of Discord webhook URLs for messages to be broadcast to
webhook_urls:
- 
```
`master` is whether or not the Discord chat is enabled in general. This can be modified here or with the `master on` and `master off` commands.
`opt_out_chat` is a list of UUIDs of players that have opted out of the chat feature. This should usually not be edited by hand; the command to add or remove yourself from this list is `discord on` or `discord off` respectively. If you are an admin, it is possible to control this setting for other players with `discord <on/off> <player_name>`
`webhook_urls` is a list of Discord webhook URLs for the bot to send messages to. All you need to do is create a webhook in the channel you want messages sent to in Discord and paste the URL here.

## Setting it up

First off you'll need a webhook in the Discord channel you want to use for Minecraft chat messages.
[Here's Discord's official guide to setting up webhooks](https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks)

This channel should not be a general talking channel and is best to be either a channel created for this purpose or a no-mic channel for voice chats.

## Building

This project can be built in any environment that supports Gradle by using `./gradlew shadowJar`. The file that should be exported to the server is `build/libs/DiscordChat-1.0-SNAPSHOT-all.jar`, as this includes the `org.json` library required for the plugin to function.
