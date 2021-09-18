package com.i0dev.SwitcherBalls.utility;

import com.google.gson.*;
import com.i0dev.SwitcherBalls.Heart;
import com.i0dev.SwitcherBalls.templates.AbstractConfiguration;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;


import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigUtil {


    public static String ObjectToJson(AbstractConfiguration object) {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create().toJson(new JsonParser().parse(new Gson().fromJson(new Gson().toJson(object), JsonObject.class).toString()));
    }

    public static JsonObject ObjectToJsonObj(Object object) {
        return new Gson().fromJson(new Gson().toJson(object), JsonObject.class);
    }

    public static JsonArray ObjectToJsonArr(Object object) {
        return new Gson().fromJson(new Gson().toJson(object), JsonArray.class);
    }

    public static Object JsonToObject(JsonElement json, Class<?> clazz) {
        return new Gson().fromJson(new Gson().toJson(json), clazz);
    }

    @SneakyThrows
    public static void save(AbstractConfiguration object, String path) {
        Files.write(Paths.get(path), ObjectToJson(object).getBytes());
    }

    public static JsonObject getJsonObject(String path) {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(path));
            return new Gson().fromJson(reader, JsonObject.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonElement getObjectFromInternalPath(String path, JsonObject json) {
        String[] paths = path.split("\\.");
        if (paths.length == 1)
            return json.get(paths[0]);
        JsonObject finalProduct = new JsonObject();
        for (int i = 0; i < paths.length - 1; i++) {
            if (i == 0) finalProduct = json.get(paths[i]).getAsJsonObject();
            else finalProduct = finalProduct.get(paths[i]).getAsJsonObject();
        }
        return finalProduct.get(paths[paths.length - 1]);
    }

    @SneakyThrows
    public static AbstractConfiguration load(AbstractConfiguration object, Heart heart) {
        String path = object.getPath();
        JsonObject savedObject = getJsonObject(path);
        if (!heart.getDataFolder().exists()) heart.getDataFolder().mkdir();
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        String configString = IOUtils.toString(Files.newBufferedReader(Paths.get(path)));
        if ("".equals(configString)) {
            save(object, path);
            return load(object, heart);
        }
        AbstractConfiguration config = new Gson().fromJson(savedObject, object.getClass());
        if (config == null) throw new IOException("The config file: [" + path + "] is not in valid json format.");
        save(config, path);
        config.setHeart(heart);
        config.setPath(path);
        System.out.println("Loaded config: " + object.getClass().getSimpleName() + " from storage.");
        return config;
    }

}
