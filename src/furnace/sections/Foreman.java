package furnace.sections;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.MethodProvider;
import settings.Settings;
import utils.Sleep;

public class Foreman extends Section {

    private boolean hasSpokeToForeman = false;

    public Foreman(MethodProvider api, Settings settings) {
        super(api, settings);
    }

    public void payForman() throws InterruptedException {
        if (api.getInventory().getAmount("Coins") < 2500) {
            settings.setQuit(true);
        } else {
            if (!hasSpokeToForeman) {
                NPC foreman = getForeman();
                if (foreman != null) {
                    if (foreman.interact("Pay")) {
                        Sleep.sleepUntil(() -> api.getDialogues().isPendingOption(), 5000);
                        hasSpokeToForeman = true;
                    }
                }
            } else {
                if (api.getDialogues().completeDialogue("Yes")) {
                    Sleep.sleepUntil(() -> !api.getDialogues().inDialogue(), 10_000);
                    settings.setShouldPayForeman(false);
                    hasSpokeToForeman = false;
                }
            }
        }
    }

    private NPC getForeman() {
        return api.getNpcs().closest("Blast Furnace Foreman");
    }
}
