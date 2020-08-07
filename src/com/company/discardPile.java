package com.company;

import javax.swing.*;
import java.awt.*;

public class discardPile extends Pile {

    public discardPile(int x, int y) {
        super(x, y);
    }

    public void paintComponent(Graphics g){
        if (mySize() == 0) {//没有牌时不画图
        }
        else{
            image = top().getImage();
            g.drawImage(image, 0, 0, this);
        }
    }

    public void addCard(Card C){
        list.add(C);
    }

}
