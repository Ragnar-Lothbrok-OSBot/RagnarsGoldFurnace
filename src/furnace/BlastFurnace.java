package furnace;

import furnace.sections.Bank;
import furnace.sections.Belt;
import furnace.sections.Dispenser;
import furnace.sections.Foreman;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.script.MethodProvider;
import settings.Settings;
import utils.Helpers;
import utils.Sleep;

public class BlastFurnace extends MethodProvider {

    private Settings settings;
    private Foreman foreman;
    private Bank bank;
    private Belt belt;
    private Dispenser dispenser;
    private Helpers helpers;

    public BlastFurnace(Settings settings) {
        this.settings = settings;
        this.foreman = new Foreman(this, this.settings);
        this.bank = new Bank(this, this.settings);
        this.belt = new Belt(this, this.settings);
        this.dispenser = new Dispenser(this, this.settings);
        this.helpers = new Helpers(this, this.settings);
    }

    public void onLoop() throws InterruptedException {
        if (settings.isShouldPayForeman() && !settings.isOreOnBelt()) {
            this.foreman.payForman();
        } else if (!settings.isOreOnBelt()) {
            if (!getInventory().contains("Gold ore")) {
                bank.bank();
            } else {
                if (!getBank().isOpen()) {
                    if (helpers.shouldDrinkStamina() && helpers.hasStaminaPotion()) {
                        Item potion = getInventory().getItem(p -> p.getName().contains("Stamina potion"));
                        if (potion != null) {
                            if (potion.interact("Drink")) {
                                Sleep.sleepUntil(() -> !helpers.shouldDrinkStamina(), 10_000);
                            }
                        }
                    } else {
                        belt.placeOre();
                    }
                } else {
                    if (getBank().close()) {
                        Sleep.sleepUntil(() -> !getBank().isOpen(), 10_000);
                    }
                }
            }
        } else {
            dispenser.collectBars();
        }
    }

}
