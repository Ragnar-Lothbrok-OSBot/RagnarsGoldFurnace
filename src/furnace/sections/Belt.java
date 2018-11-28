package furnace.sections;

import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.MethodProvider;
import settings.Settings;
import utils.Sleep;

import static org.osbot.rs07.api.ui.Skill.SMITHING;

public class Belt extends Section {

    public Belt(MethodProvider api, Settings settings) {
        super(api, settings);
    }

    public void placeOre() {
        if (!api.getEquipment().isWearingItem(EquipmentSlot.HANDS, "Goldsmith gauntlets")) {
            Item gloves = api.getInventory().getItem("Goldsmith gauntlets");
            if (gloves != null) {
                if (gloves.interact("Wear")) {
                    Sleep.sleepUntil(() -> api.getEquipment().isWearingItem(EquipmentSlot.HANDS, "Goldsmith gauntlets"), 10_000);
                }
            }
        } else {
            RS2Object belt = getBelt();
            if (belt != null) {
                if (belt.interact("Put-ore-on")) {
                    Sleep.sleepUntil(() -> !api.getInventory().contains("Gold ore") || mustPayForeman(), 10_000);
                    if (mustPayForeman()) {
                        settings.setShouldPayForeman(true);
                    } else {
                        Sleep.sleepUntil(() -> !api.getInventory().contains("Gold ore"), 10_000);
                        settings.setXpBeforePlacingOre(api.getSkills().getExperience(SMITHING));
                        settings.setOreOnBelt(true);
                    }
                }
            }
        }
    }

    private RS2Object getBelt() {
        return api.getObjects().closest(b -> b.getName().equals("Conveyor belt") && b.hasAction("Put-ore-on"));
    }

    private boolean mustPayForeman() {
        return api.getWidgets().singleFilter(api.getWidgets().getAll(),
                widget -> widget.isVisible() && (widget.getMessage().contains("You must ask the foreman's permission before using the blast<br>furnace"))) != null;
    }
}

