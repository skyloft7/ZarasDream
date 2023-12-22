import rebel.graphics.Color;
import rebel.graphics.FontRes;
import rebel.graphics.Renderer2D;
import rebel.graphics.Window;

public class Intro implements Screen {

    public FontRes fontRes;

    public String introCredit = "A game by Mohammed Baig (skyloft7)";
    public String introText = "Zara seems to be dreaming about something...";

    public Intro(){
        fontRes = new FontRes("Arial", FontRes.BOLD, 25, true);
    }

    @Override
    public void update(Window window, Renderer2D renderer2D, float time) {
        renderer2D.drawFilledRect(0, 0, renderer2D.getWidth(), renderer2D.getHeight(), Color.BLACK);

        if(time <= 1500){
            float w = fontRes.getWidthOf(introCredit);
            float h = fontRes.getLineHeight();

            float textX = renderer2D.getWidth() / 2f - w / 2;
            float textY = renderer2D.getHeight() / 2f - h / 2;

            renderer2D.drawText(textX, textY, introCredit, Color.WHITE, fontRes);
        }

        if(time >= 1500) {

            float w = fontRes.getWidthOf(introText);
            float h = fontRes.getLineHeight();

            float textX = renderer2D.getWidth() / 2f - w / 2;
            float textY = renderer2D.getHeight() / 2f - h / 2;

            renderer2D.drawText(textX, textY, introText, Color.WHITE, fontRes);

        }
    }

}
