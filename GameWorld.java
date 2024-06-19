import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Vector;
/**
 * Write a description of class MyWorld here.
 * 
 * @author Vincent Abeln  
 * @version 5.6.24
 */
public class GameWorld extends World
{

    private static int[][][] gameLevels = {
        {
            {1, 1, 1, 1, 2, 3, 100, 1},
            {1, 1, 1, 1, 1, 1, 1, 1}
        }
    };
    
    private Paddel paddel = new Paddel();
    private Ball ball = new Ball(this.paddel);
    
    public int lifes = 3;
    public int level = 1;
    
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(640, 480, 1); 
        Greenfoot.setSpeed(50);
        Greenfoot.start();
        
        this.generateWorld();
        this.drawOverlay();
    }
    
    private void drawOverlay() {
        GreenfootImage canvas = this.getBackground();
        
        canvas.setColor(Color.GRAY);
        canvas.fillRect(0, 0, 85, 25);
        canvas.fillRect(canvas.getWidth()-85, 0, canvas.getWidth(), 25);
        
        canvas.setColor(Color.WHITE);
        canvas.setFont(new Font("", 20));
        
        canvas.drawString("Lifes: " + this.lifes, 10, 20);
        canvas.drawString("Level: " + this.level, canvas.getWidth()- 75, 20);
    }
    
    private void generateWorld() {
        resetActors();
        
        this.drawLevel();
        
        this.ball.setMovement(1, 1);
    }
    
    private void drawLevel() {
        int[][] levelData = GameWorld.gameLevels[this.level - 1];
        for(int line = 0; line < levelData.length; line++) {
            if(levelData[line].length != 8) throw new Error("Invalid field data.");
            for(int row = 0; row < 8; row++) {
                int x = row * 80 + 40;
                int y = line * 25 + 40;
                
                if(levelData[line][row] != 0) this.addObject(new Block(levelData[line][row]), x, y);
            }
        }
    }
    
    private void resetActors() {
        this.removeObject(this.paddel);
        this.removeObject(this.ball);
        
        this.paddel = new Paddel();
        this.ball = new Ball(this.paddel);
        
        this.ball.setMovement(1, 1);
        
        this.addObject(paddel, this.getWidth() / 2, this.getHeight() - 50);
        this.addObject(ball, this.getWidth() / 2 - 250, this.getHeight() - 250);
    }
    
    private void finishGame() {
        this.removeObject(this.ball);
        this.paddel.locked = true;
    }
    
    public void repaint() {
        this.removeLife();
        this.drawOverlay();
    }
    
    public void removeLife() {
        this.lifes -= 1;
        if(this.lifes > 0) {
            resetActors();
            this.ball.timeout = 100;
        } else finishGame();
    }
}
