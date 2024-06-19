import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Block here.
 * 
 * @author Vincent Abeln 
 * @version 5.6.24
 */
public class Block extends Actor
{
    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    int lifes;
    
    public Block() {
        this(1);
    }
    
    public Block(int lifes) {
        this.lifes = lifes;
        this.updateImage();
    }
    
    private void updateImage() {
        switch(this.lifes) {
            case 1: this.setImage("images/brick1.png");
            break;
            case 2: this.setImage("images/brick2.png");
            break;
            default: this.setImage("images/brick3.png");
            break;
        }
    }
    
    public void removeLife() {
        this.lifes -= 1;
        this.updateImage();
        if(this.lifes < 1) this.getWorld().removeObject(this);
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
}
