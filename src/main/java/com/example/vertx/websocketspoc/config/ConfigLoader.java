package com.example.vertx.websocketspoc.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader {

    public static final String CONFIG_FILE = "application.yml";
    private static final Logger LOG = LoggerFactory.getLogger(ConfigLoader.class);

    public static Future<JsonObject> load(Vertx vertx) {
        var yamlStore = new ConfigStoreOptions()
                .setType("file")
                .setFormat("yaml")
                .setConfig(new JsonObject().put("path", CONFIG_FILE));
        var retriever = ConfigRetriever.create(vertx,
                new ConfigRetrieverOptions()
                        .addStore(yamlStore)
        );

        return retriever.getConfig();
    }
}
