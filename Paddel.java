import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Paddel here.
 * 
 * @author Vincent Abeln 
 * @version 5.6.24
 */
public class Paddel extends Actor
{
    
    public int paddelSpeed = 2;
    public boolean locked = false;
    /**
     * Act - do whatever the Paddel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        checkKeys();
    }   
    
    String lastDirection = "";
    private void checkKeys() {
        if(locked) return;
        if(Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right") && lastDirection != "left") {
            move("right", paddelSpeed);
            lastDirection = "right";
        } else if(Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) {
            move("left", paddelSpeed);
            lastDirection = "left";
        } else lastDirection = "";
        
    }
    
    private void move(String direction, int pixels) {
        if(direction == "left" && this.getX() > 120 / 2) {
            setLocation(this.getX() - pixels, this.getY());
        } else if(direction == "right" && this.getX() < this.getWorld().getWidth() - 120 / 2) {
            setLocation(this.getX() + pixels, this.getY());
        }
    }
}
