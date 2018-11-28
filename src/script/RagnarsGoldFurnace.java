package script;

import furnace.BlastFurnace;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;

import org.osbot.rs07.script.ScriptManifest;
import paint.Paint;
import settings.Settings;
import utils.Timer;

import java.awt.*;
import java.io.IOException;

import static org.osbot.rs07.api.ui.Skill.SMITHING;

@ScriptManifest(name = "Ragnar's Gold Furnace", author = "Ragnar Lothbrok", version = 1.0, info = "Ragnar Lothbroks Blast Furnace Gold Script", logo = "")

public class RagnarsGoldFurnace extends Script {

    private Settings settings = new Settings();
    private Paint paint;
    private BlastFurnace blastFurnace = new BlastFurnace(this.settings);

    @Override
    public void onStart() {
        settings.setTimer(new Timer());
        try {
            this.paint = new Paint(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.blastFurnace.exchangeContext(getBot());
        this.paint.exchangeContext(getBot());
        getExperienceTracker().start(SMITHING);
    }

    @Override
    public void onExit() {
        //Code here will execute after the script ends
    }

    @Override
    public int onLoop() throws InterruptedException {
        if (!settings.isQuit()) {
            blastFurnace.onLoop();
        } else {
            stop(true);
        }
        return 100; //The amount of time in milliseconds before the loop starts over
    }

    @Override
    public void onPaint(Graphics2D g) {
        paint.onLoop(g, getName(), getVersion());
    }

}