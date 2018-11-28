package settings;

import utils.Timer;

public class Settings {

    private Timer timer;
    private String currentAction = "Initializing Script";
    private final int foremanConfig = 1021;
    private final int furnaceConfig = 0;
    private boolean oreOnBelt = false;
    private boolean quit = false;
    private final int staminaConfig = 638;
    private long xpBeforePlacingOre = 0;
    private boolean shouldPayForeman = false;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    public int getForemanConfig() {
        return foremanConfig;
    }

    public int getFurnaceConfig() {
        return furnaceConfig;
    }

    public boolean isOreOnBelt() {
        return oreOnBelt;
    }

    public void setOreOnBelt(boolean oreOnBelt) {
        this.oreOnBelt = oreOnBelt;
    }

    public boolean isQuit() {
        return quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }

    public int getStaminaConfig() {
        return staminaConfig;
    }

    public long getXpBeforePlacingOre() {
        return xpBeforePlacingOre;
    }

    public void setXpBeforePlacingOre(long xpBeforePlacingOre) {
        this.xpBeforePlacingOre = xpBeforePlacingOre;
    }

    public boolean isShouldPayForeman() {
        return shouldPayForeman;
    }

    public void setShouldPayForeman(boolean shouldPayForeman) {
        this.shouldPayForeman = shouldPayForeman;
    }
}
