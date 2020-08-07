package com.company;


import javax.swing.*;
import java.awt.*;


public class deckPile extends Pile {
    Image image;

    public deckPile(int x, int y) {
        super(x, y);
    }

    public void paintComponent(Graphics g) {
//        System.out.println("deckpile内牌数："+mySize());
        if (mySize() == 0) {//没有牌，画NUll的图片
            image = new ImageIcon("src/images/null.jpg").getImage();
            g.drawImage(image, 0, 0, this);
        }
        else{
            image = new ImageIcon("src/images/back.jpg").getImage();
            g.drawImage(image, 0, 0,this);
        }
    }

}
