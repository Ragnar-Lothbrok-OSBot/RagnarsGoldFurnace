package paint;

import org.osbot.rs07.script.MethodProvider;
import paint.components.ProgressBar;
import settings.Settings;

import java.awt.*;
import java.io.IOException;

import static org.osbot.rs07.api.ui.Skill.SMITHING;

public class Paint extends MethodProvider  {

    private Settings settings;
    private ProgressBar progressBar;

    public Paint(Settings settings) throws IOException {
        this.settings = settings;
    }

    private final Font font = new Font("Lucida Sans Unicode", Font.BOLD, 11);
    private final int x = 8;
    private final int y = 200;
    private final int spacing = 15;
    private int lines;

    public void onLoop(Graphics2D g, String scriptName, double scriptVersion) {
        RenderingHints antialiasing = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
        g.setRenderingHints(antialiasing);
        lines = 0;
        drawOverChatBox(g);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString("[" + scriptName + " v" + scriptVersion + "]", x, nextY());
        g.drawString("Runtime: " + settings.getTimer().getElapsedToString(), x + 8, nextY());
        if (getExperienceTracker().getGainedXP(SMITHING) > 0) {
            progressBar = new ProgressBar(this, g, SMITHING, font);
            progressBar.draw(1);
        }
        Point p = mouse.getPosition();
        Dimension d = bot.getCanvas().getSize();
        g.drawLine(0, p.y, d.width, p.y);
        g.drawLine(p.x, 0, p.x, d.height);
    }

    private int nextY() {
        return y + spacing * (lines++ );
    }

    private void drawOverChatBox(Graphics2D g) {
        g.setColor(new Color(210, 194, 161));
        g.fillRect(7, 344, 506, 129);
        g.drawRect(7, 344, 506, 129);

    }

}
