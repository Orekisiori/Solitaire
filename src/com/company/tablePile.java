package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class tablePile extends Pile {
    int vInterval_down = 10;//正面朝下时牌展示间距
    int vInterval_up = 30;//正面朝上时牌展示间距
    int lastReverseCardIndex;//最后一张背面朝上的牌
    int firstUpwardsCardIndex;//第一张正面朝上的牌
    int No;//张数

    public tablePile( int x, int y,int i) {
        super(x, y);
        X = x;
        Y = y;
        No = i;
    }

    void setinitSize(int S){
        initSize = S;
    }

    public Card top() {
        return list.get(mySize()-1);
    }

    public void paintComponent(Graphics g) {
        if (mySize() == 0) {//没有牌，画NUll的图片
            image = new ImageIcon("src/images/null.jpg").getImage();
            g.drawImage(image, 0, 0, this);
        } else {
            for (int i = 0; i < last_rev_index() + 1; i++) {//先画背面朝上的牌的图片
                image = new ImageIcon("src/images/back.jpg").getImage();
                g.drawImage(image, 0, i * vInterval_down, this);
            }
            if (fir_up_index() != -1) {
                for (int i = fir_up_index(); i < mySize(); i++) {//接着画正面朝上的牌的图片
                    image = getList().get(i).getImage();
                    g.drawImage(image, 0, (last_rev_index() + 1) * vInterval_down + ((i - fir_up_index()) * vInterval_up), this);
                }
            }
        }
    }

    public boolean setPileBounds() {
        if (mySize() == 0)
            setBounds(X, Y, 57, 88);
        else//牌列不为0，根据正面朝上和背面朝上的牌的数量和牌的长度，折叠长度，计算得出总共的区域长度
            setBounds(X, Y, 57, vInterval_down * (last_rev_index() + 1) + vInterval_up * (mySize() - fir_up_index() - 1) + 88);
        return true;
    }

    public int fir_up_index() {
        for(int count=0;count<mySize();count++){
            if(list.get(count).getUpwards())
                return firstUpwardsCardIndex= count;
        }
        return -1;
    }

    public int last_rev_index() {
        for(int count=0;count<mySize();count++){
            if(list.get(count).getUpwards())
                return lastReverseCardIndex= count-1;
        }
        return -1;
    }

    public boolean isCanAdd(ArrayList<Card> checkList) {
//        System.out.println("调用isCanAdd方法");
        Card checkCard = checkList.get(0);//移动牌堆的第一张牌
        if (mySize() == 0) {
            if (checkCard.getNum() % 13 == 12)//当此列为空时，如果这张牌是K，就可以放入
                return true;
//            System.out.println("移入判断失败");
            return false;
        } else {
            Card curCard = getList().get(mySize() - 1);//接收牌堆的最后一张牌
            boolean checkType = ((checkCard.getType()%2) != (curCard.getType()%2));//判断花色不同
            boolean checkNum = (curCard.getNum() - checkCard.getNum() == 1);//判断数字差1
            if (checkNum && checkType){
//                System.out.println("判断成功");
                return true;
            }
//            System.out.println("移入判断失败");
            return false;
        }
    }

    public ArrayList<Card> checkXY(int x, int y) {
        if (mySize() == 0)
            return null;
        int n;
        //根据正面朝上和背面朝上的牌的数量，和牌的长度,和牌被折叠的长度来分别判断是否可移动
        if (y > (last_rev_index() + 1) * vInterval_down + (mySize() - fir_up_index() - 1) * vInterval_up) {
            ArrayList<Card> list = new ArrayList<>(1);
            list.add(getList().get(mySize() - 1));
//            System.out.println("可移动："+list.size()+"张");
            return list;
        }
        if (y - (last_rev_index() + 1) * vInterval_down < 0)
            return null;
        n = (y - (last_rev_index() + 1) * vInterval_down) / vInterval_up;

        if (n < 12 && n >= 0 && fir_up_index() >= 0) {
            ArrayList<Card> list = new ArrayList<>();
            for (int i = n + fir_up_index(); i < mySize(); i++) {
                list.add(getList().get(i));
            }
//            System.out.println("可移动："+list.size()+"张");
            return list;
        } else
            return null;
    }

    public void reverse() {
        if(mySize() != 0){
            if(!(list.get(mySize()-1).isUpward())){
                list.get(mySize()-1).setUpwards(true);
            }
        }
    }

}
