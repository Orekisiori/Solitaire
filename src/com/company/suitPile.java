package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class suitPile extends Pile {

    public suitPile(int x, int y) {
        super(x, y);
    }

    public void paintComponent(Graphics g){
        if (mySize() == 0) {//没有牌，画NUll的图片
            image = new ImageIcon("src/images/null.jpg").getImage();
            g.drawImage(image, 0, 0, this);
        }
        else{
            image = top().getImage();
            g.drawImage(image, 0, 0,this);
        }
    }

    public boolean isCanAdd(ArrayList<Card> checkList) {
        if(checkList == null)
            return  false;
        if(checkList.size() == 1){
            Card checkCard = checkList.get(0);//移动牌堆的第一张牌
            if (mySize() == 0) {
                if (checkCard.getNum() % 13 == 0)//当此列为空时，如果这张牌是A，就可以放入
                    return true;
                return false;
            } else {
                Card curCard = getList().get(mySize() - 1);//接收牌堆的最后一张牌
                boolean checkType = (checkCard.getType() == curCard.getType());//判断花色相同
                boolean checkNum = (curCard.getNum() - checkCard.getNum() == -1);//判断数字差1
                if (checkNum && checkType){
                    return true;
                }
                return false;
            }
        }
        return false;
    }

}
