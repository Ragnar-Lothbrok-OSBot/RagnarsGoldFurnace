package furnace.sections;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;
import settings.Settings;
import utils.Helpers;
import utils.Sleep;

import java.util.Optional;

public class Bank extends Section {

    private Helpers helpers;

    public Bank(MethodProvider api, Settings settings) {
        super(api, settings);
        this.helpers = new Helpers(this.api, this.settings);
    }

    public void bank() throws InterruptedException {
        if (!api.getBank().isOpen()) {
            if (api.getBank().open()) {
                Sleep.sleepUntil(() -> api.getBank().isOpen(), 10_000);
            }
        } else {

            if (api.getInventory().contains("Gold bar")) {
                if (api.getBank().depositAll("Gold bar")) {
                    Sleep.sleepUntil(() -> !api.getInventory().contains("Gold bar"), 10_000);
                }
            } else if (api.getInventory().contains("Vial")) {
                if (api.getBank().depositAll("Vial")) {
                    Sleep.sleepUntil(() -> !api.getInventory().contains("Vial"), 10_000);
                }
            } else if (helpers.shouldDrinkStamina() && !helpers.hasStaminaPotion() && api.getBank().contains(p -> p.getName().contains("Stamina potion"))) {
                Optional<Item> potion = helpers.getStaminaPotion();
                if (potion.isPresent()) {
                    if (api.getBank().withdraw(potion.get().getName(), 1)) {
                        Sleep.sleepUntil(() -> api.getInventory().contains(potion.get().getName()), 10_000);
                    }
                }
            } else {
                if (api.getBank().contains("Gold ore")) {
                    if (api.getBank().withdrawAll("Gold ore")) {
                        Sleep.sleepUntil(() -> api.getInventory().isFull(), 10_000);
                    }
                } else {
                    settings.setQuit(true);
                }
            }
        }
    }

}
