package utils;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;
import settings.Settings;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class Helpers {

    private MethodProvider api;
    private Settings settings;

    public Helpers(MethodProvider api, Settings settings) {
        this.api = api;
        this.settings = settings;
    }

    public boolean shouldDrinkStamina() {
        return api.getConfigs().get(settings.getStaminaConfig()) < 1 && api.getSettings().getRunEnergy() < 70;
    }

    public boolean hasStaminaPotion() {
        return api.getInventory().contains(p -> p.getName().contains("Stamina potion"));
    }

    public Optional<Item> getStaminaPotion() {
        return Arrays.stream(api.getBank().getItems())
                .filter(item -> item != null && item.getName().startsWith("Stamina potion"))
                .min(Comparator.comparing(Item::getName));
    }

}
