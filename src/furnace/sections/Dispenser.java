package furnace.sections;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.Condition;
import settings.Settings;
import utils.Sleep;

import static org.osbot.rs07.api.ui.Skill.SMITHING;

public class Dispenser extends Section {

    private Position walkPosition = new Position(1939, 4963, 0);

    public Dispenser(MethodProvider api, Settings settings) {
        super(api, settings);
    }

    public void collectBars() throws InterruptedException {
        Item gloves = api.getInventory().getItem("Ice gloves");
        if (api.getSkills().getExperience(SMITHING) == settings.getXpBeforePlacingOre()) {
            if (!api.myPosition().equals(walkPosition)) {
                WalkingEvent walkingEvent = new WalkingEvent(walkPosition);
                walkingEvent.setMiniMapDistanceThreshold(0);
                walkingEvent.setBreakCondition(new Condition() {
                    @Override
                    public boolean evaluate() {
                        return api.getSkills().getExperience(SMITHING) > settings.getXpBeforePlacingOre();
                    }
                });
                api.execute(walkingEvent);
            } else {
                if (gloves != null) {
                    gloves.hover();
                }
            }
        } else {
            if (!api.getEquipment().isWearingItem(EquipmentSlot.HANDS, "Ice gloves")) {
                if (gloves != null) {
                    if (gloves.interact("Wear")) {
                        Sleep.sleepUntil(() -> api.getEquipment().isWearingItem(EquipmentSlot.HANDS, "Ice gloves"), 10_000);
                    }
                }
            } else {
                if (!api.getDialogues().inDialogue()) {
                    RS2Object dispenser = getDispenser();
                    if (dispenser != null) {
                        if (dispenser.interact("Take")) {
                            Sleep.sleepUntil(() -> api.getDialogues().inDialogue(), 10_000);

                        }
                    }
                } else {
                    api.getKeyboard().pressKey(32);
                    api.sleep(1000);
                    if (api.getInventory().contains("Gold bar")) {
                        settings.setOreOnBelt(false);
                    }
                }
            }
        }
    }

    private RS2Object getDispenser() {
        return api.getObjects().closest(b -> b.getName().equals("Bar dispenser") && b.hasAction("Take"));
    }
}
