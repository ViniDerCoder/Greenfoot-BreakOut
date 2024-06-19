import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Ball here.
 * 
 * @author Vincent Abeln 
 * @version 5.6.24
 */
public class Ball extends Actor
{
    /**
     * Act - do whatever the Ball wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private int[] movement = {0, 0};
    private Paddel linkedPaddel;
    
    public Ball(Paddel linkedPaddel) {
        this.linkedPaddel = linkedPaddel;
    }
    
    public int timeout = 100;
    
    public void act() {
        if(timeout > 0) timeout -= 1;
        else move();
    }   
    
    public void setMovement(int movementX, int movementY) {
        movement[0] = movementX;
        movement[1] = movementY;
    }
    
    private void move() {
        int newX = this.getX() + movement[0];
        int newY = this.getY() + movement[1];
        
        this.setLocation(this.getX() + movement[0], this.getY() + movement[1]);
        
        if(this.getOneIntersectingObject(Paddel.class) != null) {
            this.movement[1] = -this.movement[1];
            /*int paddelStart = this.linkedPaddel.getX() - this.linkedPaddel.getImage().getWidth() / 2;
            int paddelWidth = this.linkedPaddel.getImage().getWidth();
            int relativeBallX = this.getX() - paddelStart;
            
            if(relativeBallX < 0) relativeBallX = 0;
            if(relativeBallX > paddelWidth) relativeBallX = paddelWidth;
            
            System.out.println(relativeBallX + "/" + paddelWidth);
            double percentage = (double)relativeBallX / (double)paddelWidth;
            double orientedPercentage = percentage - 0.5;
            
            this.movement[1] = this.movement[1] + this.movement[1] * percentage);
            
            System.out.println(orientedPercentage);*/
        }
        List<Block> intersectingBlocks = this.getIntersectingObjects(Block.class);
        if(intersectingBlocks.size() > 0) {
            this.movement[1] = -this.movement[1];
            for(Block block : intersectingBlocks) {
                block.removeLife();
            }
        }
        
        if(newX >= this.getWorld().getWidth() - 28 / 2 || newX <= 0 + 28 / 2) this.movement[0] = -this.movement[0];
        if(newY <= 0 + 28 / 2) this.movement[1] = -this.movement[1];
        else if(newY >= this.getWorld().getHeight() - 28 / 2) this.getWorld().repaint();
    }
}
