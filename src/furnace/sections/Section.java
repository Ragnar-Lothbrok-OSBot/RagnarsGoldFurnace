package furnace.sections;

import org.osbot.rs07.script.MethodProvider;
import settings.Settings;

public abstract class Section {

    protected MethodProvider api;
    protected Settings settings;

    public Section(MethodProvider api, Settings settings) {
        this.api = api;
        this.settings = settings;
    }

}
