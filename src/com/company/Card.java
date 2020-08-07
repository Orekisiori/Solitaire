package com.company;

import java.awt.*;

public class Card {
    int num;//点数
    int type;//花色
    static int width = 57;
    static int height = 88;
    boolean isUpwards;//正or反面朝上
    Image image;

    int getNum(){return num;}

    int getType(){return type;}

    Image getImage(){return image;}

    boolean isUpward(){return  isUpwards;}

    void setUpwards(boolean B){
        isUpwards = B;
    }

    boolean getUpwards(){return isUpwards;}

    public Card(int j, int i, boolean uw, Image I){
        type = i;
        num = j;
        isUpwards = uw;
        image = I;
        isUpwards = false;
    }

}
