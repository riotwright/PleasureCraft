package com.sandymandy.pleasurecraft.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PleasureCraftLangUtils {
    private static final Map<String, String> translations = new HashMap<>();
    private static final Gson GSON = new Gson();

    static {
        try {
            // Load your mod's en_us.json from resources
            Identifier id = Identifier.of("pleasurecraft", "lang/en_us.json");
            try (InputStreamReader reader = new InputStreamReader(
                    Objects.requireNonNull(PleasureCraftLangUtils.class.getClassLoader().getResourceAsStream("assets/" + id.getNamespace() + "/" + id.getPath())),
                    StandardCharsets.UTF_8)) {
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                Map<String, String> data = GSON.fromJson(reader, type);
                if (data != null) translations.putAll(data);
            }
        } catch (Exception e) {
            System.err.println("[PleasureCraft] Failed to load server translations: " + e.getMessage());
        }
    }

    public static String getStringFromKey(String key, Object... args) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                return net.minecraft.client.resource.language.I18n.translate(key, args);
            } catch (Throwable ignored) {}
        }
        String base = translations.getOrDefault(key, key);
        return args.length > 0 ? String.format(base, args) : base;
    }
}
