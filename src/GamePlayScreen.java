import org.joml.Vector2f;
import rebel.Animation;
import rebel.Input;
import rebel.Math;
import rebel.Time;
import rebel.graphics.*;
import rebel.particles.Particle;
import rebel.particles.ParticleSource;
import rebel.particles.ParticleSourceConfig;

import java.util.ArrayList;
import java.util.Iterator;

public class GamePlayScreen implements Screen {
    private ArrayList<Tile> tiles;
    private float tileSpeedFactor = 1;
    private float tileSpeedY = 150;
    private Texture tileTexture;
    private Animation zaraAnimation;
    private ParticleSource harmonyParticleSource;
    private FontRes fontRes;
    private ArrayList<String> dialogue = new ArrayList<>();
    private long dialogueStartTime;
    private int dialogueIndex = 0;
    private String currentDialogue = "<undetermined_dialogue>";
    private boolean gameStart = false;

    private ArrayList<Line> playerLines = new ArrayList<>();
    private long lastMouseTime;
    private float lastMouseX, lastMouseY;

    private float dialogueDelay = 800;

    public GamePlayScreen(Renderer2D renderer2D) {
        GameManager.fails = 0;
        Texture tileTexture = new Texture("logo.png");
        tiles = Tile.createTiles(GameManager.tileNums, renderer2D, tileTexture);


        zaraAnimation = new Animation(new Texture("ZaraIdleOther.png", Texture.FILTER_NEAREST));
        zaraAnimation.setPlaymode(Animation.Playmode.PLAY_REPEAT);
        zaraAnimation.setDelay(120);
        zaraAnimation.create(2, 2, 4);

        this.tileTexture = new Texture("logo.png");
        ParticleSourceConfig particleSourceConfig = new ParticleSourceConfig(15, 15, 2000, 2, 5, 5);
        harmonyParticleSource = new ParticleSource(particleSourceConfig, new Vector2f(renderer2D.getWidth() - 200, renderer2D.getHeight() - 200));
        harmonyParticleSource.addParticles(200);

        fontRes = new FontRes("Arial", FontRes.BOLD, 25, true);


        dialogue.add("???: Hello Zara...");
        dialogue.add("Zara: Where -- what is this?");
        dialogue.add("???: This is your dream, and those are your memories.");
        dialogue.add("Zara: And you are??");
        dialogue.add("Harmony: I'm Harmony!");
        dialogue.add("Harmony: You see, there's a bit of a problem.");
        dialogue.add("Harmony: You see, there's a bit of a problem..");
        dialogue.add("Harmony: You see, there's a bit of a problem...");
        dialogue.add("Harmony: You must collect the good memories before they fade away!");
        dialogue.add("Harmony: *cough* And there's only " + GameManager.chances + " chances *cough*");
        dialogue.add("Zara: ...");
        dialogue.add("Zara: Let's");
        dialogue.add("Zara: Let's do");
        dialogue.add("Zara: Let's do this");
        dialogue.add("Harmony: Good luck, Zara <e_start>");
    }
    @Override
    public void update(Window window, Renderer2D renderer2D, float time) {

        //Zara + Harmony Rendering
        {
            zaraAnimation.update(Time.deltaTime);


            for (Iterator<Tile> iterator = tiles.iterator(); iterator.hasNext(); ) {
                Tile tile = iterator.next();
                renderer2D.drawTexture(tile.rect2D.x, tile.rect2D.y, tile.rect2D.w, tile.rect2D.h, tile.texture);

                if(gameStart) {
                    if (tile.rect2D.y >= renderer2D.getHeight()) {
                        GameManager.fails++;
                    }

                    boolean hit = false;

                    for (Line line : playerLines){
                        if(tile.rect2D.contains(line.start.x, line.start.y) || tile.rect2D.contains(line.end.x, line.end.y)){
                            hit = true;
                            break;
                        }
                    }


                    if (tile.rect2D.contains(window.getMouseX(), window.getMouseY()) || hit) {
                        GameManager.tileKillCount++;
                        iterator.remove();
                    }
                }
                if (tile.rect2D.y >= renderer2D.getHeight()) {
                    tile.rect2D.y = -tile.rect2D.h;
                }
                tile.rect2D.y += tileSpeedY * Time.deltaTime * tileSpeedFactor;
            }



            renderer2D.drawTexture(0, renderer2D.getHeight() - 24 * 10, 24 * 10, 24 * 10, zaraAnimation.getTexture(), Color.WHITE, zaraAnimation.getCurrentFrame());

            harmonyParticleSource.update();
            for (Particle particle : harmonyParticleSource.getParticles()) {
                renderer2D.drawFilledEllipse(particle.rect2D.x, particle.rect2D.y, particle.rect2D.w, particle.rect2D.h, new Color(51 / 255f, 153 / 255f, 255 / 255f, 1f));
            }

            if(gameStart) {

                String livesText = "Lives: " + GameManager.getRemainingChances();

                renderer2D.drawFilledRect(0, 0, fontRes.getWidthOf(livesText), fontRes.getHeightOf(livesText), Color.WHITE);
                renderer2D.drawText(0, 0, livesText, Color.BLACK, fontRes);
            }
        }
        //Dialogue
        {
            if(window.isKeyPressed(Input.REBEL_KEY_SPACE)){
                for (int i = 0; i < dialogue.size(); i++) {
                    String dialogueText = dialogue.get(i);

                    if(dialogueText.contains("<e_")){
                        dialogueIndex = i;
                        break;
                    }

                }
            }



            if (System.currentTimeMillis() - dialogueStartTime >= dialogueDelay) {
                String dialogueText = dialogue.get(dialogueIndex);
                if (dialogueIndex != dialogue.size() - 1)
                    dialogueIndex++;
                if (dialogueText.contains("<e_speed_up>")) {
                    tileSpeedFactor = 2f;
                }
                if (dialogueText.contains("<e_start>") && !gameStart) {

                    tiles.clear();
                    tiles = Tile.createTiles(GameManager.tileNums, renderer2D, tileTexture);


                    gameStart = true;
                }
                currentDialogue = dialogueText
                        .replaceAll("<e_speed_up>", "")
                        .replaceAll("<e_start>", "");
                dialogueStartTime = System.currentTimeMillis();
            }

            float w = fontRes.getWidthOf(currentDialogue);
            float h = fontRes.getHeightOf(currentDialogue);

            float textX = renderer2D.getWidth() / 2f - w / 2;
            float textY = 200 + renderer2D.getHeight() / 2f - h / 2;

            renderer2D.drawFilledRect(textX, textY, w, h, Color.WHITE);
            renderer2D.drawText(textX, textY, currentDialogue, Color.BLACK, fontRes);
        }
        //Trailing Cursor
        {
            if(System.currentTimeMillis() - lastMouseTime >= 5){
                playerLines.add(new Line(new Vector2f(lastMouseX, lastMouseY), new Vector2f(window.getMouseX(), window.getMouseY())));
                lastMouseTime = System.currentTimeMillis();
                if(playerLines.size() > 5){
                    playerLines.remove(0);
                }

                lastMouseX = window.getMouseX();
                lastMouseY = window.getMouseY();
            }

            int thickness = 10;
            float opacity = 0f;

            for (int i = 0; i < playerLines.size(); i++) {
                Line line = playerLines.get(i);
                renderer2D.drawLine(line.start.x, line.start.y, line.end.x, line.end.y, new Color(1f, 0f, 0f, opacity), thickness, true);
                thickness += 1;
                opacity = Math.clamp(opacity + 0.2f, 0f, 1f);
            }
        }



    }

}
