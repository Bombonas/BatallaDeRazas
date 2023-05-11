package clases;

import java.awt.image.BufferedImage;

public class CharacterAnimationDetails {
    //Class will handle necessary values to animate every character according to their needs
    private String name;
    private BufferedImage image;
    private int frameCount, currentFrame, posX, posY;

    //Define constructor

    public CharacterAnimationDetails(String name, BufferedImage image, int frameCount, int posX, int posY) {
        this.name = name;
        this.image = image;
        this.frameCount = frameCount;
        this.posX = posX;
        this.posY = posY;
        this.currentFrame = 0;
    }

    //Define getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    //updateFrame() method moves across the X axis of the character sprite sheet and returns the corresponding frame
    //necessary to get every image part of a character's animation
    public BufferedImage updateFrame() {
        this.currentFrame = (this.currentFrame + 1) % this.frameCount;
        return this.image.getSubimage(currentFrame*posX, 0, posX, posY);
    }
}
