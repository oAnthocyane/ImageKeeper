# ImageKeeper

**ImageKeeper** is a versatile image management tool that allows you to easily organize and retrieve your photos using keywords. Whether you want to join groups to share photos or keep your personal collection organized, ImageKeeper has you covered. This repository contains three services:

1. **RESTful Server**: A robust server that handles various POST and GET requests, interacts with a database, and communicates with the Google Drive API.

2. **Telegram Bot**: An interactive Telegram bot that connects to the RESTful server and provides a user-friendly interface for managing your images and groups. Here are some of the commands you can use with the bot:

    - `/start`: Begin interaction with the bot and add yourself to the user database.
    - `/leave_group <group_name>`: Leave a specific group.
    - `/join_group <group_name> <password>`: Join a group by specifying its name and password.
    - `/help`: Get information about the bot and available commands.
    - `/change_language <language>` : change language(en/ru)
    - `/get_groups`: List all the groups you are currently a part of.
    - `/findByUniqPhraseAndGroups <unique_phrase> <group_names>`: Search for an image by a unique phrase within selected groups.
    - `/findByUniqPhraseAndAllGroups <unique_phrase>`: Search for an image by a unique phrase across all groups.
    - `/findByUniqPhrase <unique_phrase>`: Find an image by a unique phrase.
    - `/findByKeyPhrasesAndGroups -k <key_phrases> -g <groups>`: Search for images using key phrases within specific groups.
    - `/findByKeyPhrasesAndAllGroups <key_phrases>`: Search for images using key phrases across all groups.
    - `/findByKeyPhrase <key_phrases>`: Search for images by key phrases.
    - `/find <keyword> <other_arguments>`: A simplified search command, where you specify the keyword followed by the required parameters for the desired command. 
   
         The keywords for the commands are:

          -u - findByUniqPhrase
          
          -ug - findByUniqPhraseAndGroups
          
          -uag - findByUniqPhraseAndAllGroups
          
          -k - findByKeyPhrase
          
          -kg - findByKeyPhrasesAndGroups
          
          -kag - findByKeyPhrasesAndAllGroups

    To add an image using the Telegram bot, simply send a photo and provide a caption with the following format:

   <unique_name> -k <keywords> -g <group_names>

3. **AdminServer**: This server is responsible for monitoring the status of the RESTful server. It uses Spring Admin for this purpose.


Use ImageKeeper to effortlessly manage your image collection, search for photos, and collaborate within groups. Happy image keeping! 📷🔍✨
