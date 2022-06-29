package ru.job4j.thread;

import java.util.HashMap;
import java.util.Map;

final public class ArgsName {

    final private Map<String, String> values = new HashMap<>();

    public String get(String key) {
        return values.get(key);
    }

    private void parse(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Missing arguments. At least there must be -url=FileURL -speed=SPEED");
        }
        for (String a : args) {
            if (!a.startsWith("-") || a.split("=").length < 2) {
                throw new IllegalArgumentException("Incorrect argument(s) format: must be -ARG=VALUE");
            }
            values.put(a.split("=")[0].split("-")[1], a.split("=")[1]);
        }
    }

    static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}
