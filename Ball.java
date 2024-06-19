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
    private GameWorld world;
    private double speed = 0;
    
    public Ball(GameWorld world, Paddel linkedPaddel) {
        this.linkedPaddel = linkedPaddel;
        this.world = world;
    }
    
    public int timeout = 100;
    
    public void act() {
        if(timeout > 0) timeout -= 1;
        else move();
    }   
    
    public void setMovement(int movementX, int movementY) {
        movement[0] = movementX;
        movement[1] = movementY;
        this.speed = getSpeed();
    }
    
    public double getSpeed() {
        return Math.sqrt((movement[0] * movement[0]) + (movement[1] * movement[1]));
    }
    
    public double getCorrespondingMovmentComponent(double comp) {
        return Math.sqrt((this.speed * this.speed) - (comp * comp));
    }
    
    private void move() {
        int newX = this.getX() + movement[0];
        int newY = this.getY() + movement[1];
        
        this.setLocation(this.getX() + movement[0], this.getY() + movement[1]);
        
        if(this.getOneIntersectingObject(Paddel.class) != null) {
            int paddelStart = this.linkedPaddel.getX() - this.linkedPaddel.getImage().getWidth() / 2;
            int paddelWidth = this.linkedPaddel.getImage().getWidth();
            int relativeBallX = this.getX() - paddelStart;
            
            if((relativeBallX < 0 && this.movement[0] >= 0) || (relativeBallX > paddelWidth && this.movement[0] <= 0)) {
                this.movement[0] = -this.movement[0];
            } else {
                double percentage = (double)relativeBallX / (double)paddelWidth;
                double orientedPercentage = (percentage - 0.5) * 2;
                
                this.movement[0] = (int)Math.round(getCorrespondingMovmentComponent(this.movement[0]) * orientedPercentage);
                this.movement[1] = -(int)Math.floor(getCorrespondingMovmentComponent(Math.abs((double)this.movement[1] * percentage)));
            }
        }
        List<Block> intersectingBlocks = this.getIntersectingObjects(Block.class);
        if(intersectingBlocks.size() > 0) {
            //Add rebouncing for block
            for(Block block : intersectingBlocks) {
                if(this.getX() + this.getImage().getWidth() / 2 > block.getX() - block.getImage().getWidth() / 2 && this.getX() - this.getImage().getWidth() / 2 < block.getX() + block.getImage().getWidth() / 2 ) {
                    this.movement[1] = -this.movement[1];
                } else if(this.getY() + this.getImage().getHeight() / 2 < block.getY() + block.getImage().getHeight() / 2 || this.getY() - this.getImage().getHeight() / 2 > block.getY() - block.getImage().getHeight() / 2) {
                    this.movement[0] = -this.movement[0];
                }
                block.removeLife();
            }
            if(this.world.getObjects(Block.class).size() <= 0) this.world.nextLevel();
        }
        
        if(newX >= this.world.getWidth() - 28 / 2 || newX <= 0 + 28 / 2) this.movement[0] = -this.movement[0];
        if(newY <= 0 + 28 / 2) this.movement[1] = -this.movement[1];
        else if(newY >= this.world.getHeight() - 28 / 2) this.world.removeLife();
    }
}
