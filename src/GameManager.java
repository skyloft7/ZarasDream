public class GameManager {
    public static int fails = 0;
    public static int chances = 20;
    public static int tileKillCount = 0;
    public static int tileNums = 100;

    public static int getRemainingChances(){
        return chances - fails;
    }
}
