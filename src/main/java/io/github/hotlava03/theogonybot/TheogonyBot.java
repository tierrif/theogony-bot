package io.github.hotlava03.theogonybot;

import io.github.hotlava03.baclavaframework.BaclavaFramework;
import io.github.hotlava03.baclavaframework.BaclavaFrameworkBuilder;
import io.github.hotlava03.theogonybot.util.UtilClasses;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;

public class TheogonyBot {
    private UtilClasses utilClasses;
    private BaclavaFramework framework;
    private OkHttpClient httpClient;

    public static void main(String[] args) throws LoginException {
        new TheogonyBot().init();
    }

    public void init() throws LoginException {
        this.framework = new BaclavaFrameworkBuilder()
                .setCommandRegisterer(new Registerer(this))
                .setOwnerId(362753440801095681L)
                .setToken(System.getenv("THEOGONY_TOKEN"))
                .setPrefix("/")
                .build();
        this.utilClasses = new UtilClasses(this);
        this.httpClient = new OkHttpClient();
        this.framework.onReady(e -> this.framework.getLogger(this.getClass()).info("Finished loading."));
    }

    public UtilClasses getUtilClasses() {
        return this.utilClasses;
    }

    public BaclavaFramework getFramework() {
        return framework;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }
}
