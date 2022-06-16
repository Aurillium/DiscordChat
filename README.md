# DiscordChat
A Spigot plugin designed to allow your Minecraft chat to show up in Discord.


## Config

Your configuration file will look like this:
```yml
chat_master: true
opt_out_chat:
- 
webhook_urls:
- 
```
`chat_master` is whether or not the Discord chat is enabled in general. This can be modified here or with the `masterchatoff` and `masterchaton` commands.
`webhook_urls` is a list of Discord webhook URLs for the bot to send messages to. All you need to do is create a webhook in the channel you want messages sent to in Discord and paste the URL here.

## Setting it up

First off you'll need a webhook in the Discord channel you want to use for Minecraft chat messages.
[Here's Discord's official guide to setting up webhooks](https://support.discord.com/hc/en-us/articles/228383668-Intro-to-Webhooks)

This channel should not be a general talking channel and is best to be either a channel created for this purpose or a no-mic channel for voice chats.
