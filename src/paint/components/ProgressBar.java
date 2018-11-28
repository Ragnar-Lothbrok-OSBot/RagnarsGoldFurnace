package paint.components;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.MethodProvider;
import utils.Format;

import java.awt.*;
import java.util.HashMap;

import static org.osbot.rs07.api.ui.Skill.SMITHING;

public class ProgressBar {

    private MethodProvider api;
    private Graphics2D g;
    private Skill skill;
    private Font font;
    private int frameTopY = 344;
    private int frameLeftX = 7;
    private int frameHeight = (126 / 2);
    private int frameWidth = (506 / 2);
    private int height = ((frameHeight - 10) / 3);
    private int width = (frameWidth - 10);

    private Color grey = Color.GRAY;

    private int[] XP = new int[]{0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431, 200000000};
    private HashMap<Skill, Color> colors = new HashMap<>();

    public ProgressBar(MethodProvider api, Graphics2D g, Skill skill, Font font) {
        this.api = api;
        this.g = g;
        this.skill = skill;
        this.font = font;

        colors.put(SMITHING, grey);
    }

    public void draw(int index) {
        int x = 0;
        int y = 0;
        boolean line = false;

        switch (index) {
            case 1:
                x = frameLeftX + 5;
                y = frameTopY + 5;
                line = true;
                break;
            case 2:
                x = frameLeftX + 5;
                y = (frameTopY + 5) + frameHeight;
                break;
            case 3:
                x = (frameLeftX + frameWidth) + 5;
                y = frameTopY + 5;
                line = true;
                break;
            case 4:
                x = (frameLeftX + frameWidth) + 5;
                y = (frameTopY + 5) + frameHeight;
                break;
        }

        g.setColor(Color.BLACK);

        drawCenteredString("Skill: " + skill.toString(), new Rectangle(x, y, (width / 3), height));
        drawCenteredString("Level: " + api.getSkills().getStatic(skill), new Rectangle(x + (width / 3), y, (width / 3), height));
        drawCenteredString("TTL: " + Format.msToString(api.getExperienceTracker().getTimeToLevel(skill)), new Rectangle(x + ((width / 3) * 2), y, (width / 3), height));

        g.setColor(colors.get(skill));
        g.fillRect(x, y + 20, getWidth(getPercentage(getCurrentExperience(), getTargetExperience()), width), height);

        g.setColor(Color.BLACK);

        drawCenteredString("Xp: " + formatValue(api.getExperienceTracker().getGainedXP(skill)), new Rectangle(x, y + 40, (width / 3), height));
        drawCenteredString("Levels: " + api.getExperienceTracker().getGainedLevels(skill), new Rectangle(x + (width / 3), y + 40, (width / 3), height));
        drawCenteredString("Xp/Hr: " + formatValue(api.getExperienceTracker().getGainedXPPerHour(skill)), new Rectangle(x + ((width / 3) * 2), y + 40, (width / 3), height));

        if (line) {
            g.drawLine(x, y + 60, x + width, y + 60);
        }
    }

    private void drawCenteredString(String text, Rectangle rect) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    private int getPercentage(int current, int total) {
        return current * 100 / total;
    }

    private int getWidth(int percentage, int totalWidth) {
        return percentage * totalWidth / 100;
    }

    private int getCurrentExperience() {
        return api.getSkills().getExperience(skill) - XP[api.getSkills().getStatic(skill) - 1];
    }

    private int getTargetExperience() {
        return XP[api.getSkills().getStatic(skill)] - XP[api.getSkills().getStatic(skill) - 1];
    }

    private static final String formatValue(long l) {
        return l > 1000000L ? String.format("%.2fm", (double)l / 1000000.0D) : (l > 1000L ? String.format("%.1fk", (double)l / 1000.0D) : String.valueOf(l));
    }

}