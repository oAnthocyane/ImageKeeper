package antne.imagekeeper.telegrambot.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlagParser {
    public static Map<String, List<String>> parse(String[] arguments){
        Map<String, List<String>> parsedPhrase = new HashMap<>();
        String key = "";
        for (String argument : arguments) {
            if(argument.startsWith("-")){
                key = argument;
                parsedPhrase.put(key, new ArrayList<>());
            }
            else if (parsedPhrase.containsKey(key)) parsedPhrase.get(key).add(argument);
        }
        return parsedPhrase;
    }
}
