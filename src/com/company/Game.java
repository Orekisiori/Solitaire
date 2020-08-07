package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    int KEEP;
    int x;
    int y;
    String str;
    boolean releaseFlag;
    static ArrayList<Card> allCard;
    static Pile[] allPiles;
    static suitPile[] SuitPiles;
    static tablePile[] TablePiles;
    static JPanel jp;

    tablePile fromTab;
    tablePile toTab;
    suitPile toSuit;
    ArrayList<Card> tempList;
    deckPile DeckPile;
    discardPile DiscardPile;
    boolean dragFlag;
    int pressX;
    int pressY;

    public Game() {
        JFrame f = new JFrame("Solitaire Game");
        f.setLocation(160, 90);
        f.setSize(1080, 640);
        f.setResizable(false);
        f.setVisible(true);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);

        Color c = new Color(103, 197, 108);
        jp = new JPanel();
        jp.setBackground(c);
        jp.setLayout(null);

        f.getContentPane().add(jp);

        init();

        for(int count = 0;count < 13;count++){
            jp.add(allPiles[count]);
        }

        begin();

    }

    void init() {
        //初始化牌堆
        allPiles = new Pile[13];
        SuitPiles = new suitPile[4];
        TablePiles = new tablePile[7];
        // then fill them in
        allPiles[0] = DeckPile = new deckPile(200, 40);
        allPiles[1] = DiscardPile = new discardPile(200 + Card.width + 50, 40);
        for (int i = 0; i < 4; i++)//四个suit牌堆，即最终放置排好的四列牌的地方
        {
            allPiles[2 + i] = SuitPiles[i] = new suitPile(200 + Card.width + 50 + Card.width + 150 + (40 + Card.width) * i, 40);
        }
        for (int i = 0; i < 7; i++)//七个table牌堆，即游戏主要操作的牌堆
        {
            allPiles[6 + i] = TablePiles[i] = new tablePile(200 + (50 + Card.width) * i, 40 + Card.height + 40, i);
            TablePiles[i].setinitSize(i+1);
        }

        allCard = new ArrayList<Card>();
        int k = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j <= 12; j++) {
                k++;
                str=String.format("src/images/%d.jpg", k);
                allCard.add(new Card(j, i,false,new ImageIcon(str).getImage()));//生成牌
            }

        Random generator = new Random();//随机打乱牌序
        for (int i = 0; i < 52; i++) {
            int j = Math.abs(generator.nextInt() % 52);
            // swap the two card values
            Card fromTab = allCard.get(i);
            allCard.set(i, allCard.get(j));
            allCard.set(j, fromTab);
        }



        for (int i = 0; i < 7; i++) {
            ArrayList<Card> al = new ArrayList<Card>();
            for (int j = 0; j < TablePiles[i].initSize; j++) {
                al.add(allCard.remove(allCard.size() - 1));//从allcard中依次取出最底卡牌，加入al中（1~7张），然后al直接加入tablepile中
            }
            TablePiles[i].addCard(al);
            TablePiles[i].top().setUpwards(true);
            TablePiles[i].setPileBounds();
        }//把牌发到7列桌面牌列Tablepile中，分别发1~7张

        int rest =  allCard.size();
        for(int i = 0;i < rest;i++ ){
            DeckPile.addCard(allCard.remove(allCard.size()-1));
        }

    }

    public static void checkWIN(){
        if(SuitPiles[0].mySize()==13&&SuitPiles[1].mySize()==13&&SuitPiles[2].mySize()==13&&SuitPiles[3].mySize()==13){
            JOptionPane.showMessageDialog(null,"YOU WIN!","Congratulation!",JOptionPane.PLAIN_MESSAGE);
        }
    }

    void begin(){//为组件添加监听器
        for (int tabindex = 0; tabindex < 7; tabindex++) {
            int finalTabindex = tabindex;
            TablePiles[tabindex].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
//                    System.out.println("方块"+ finalTabindex +"鼠标移入事件");
                    if(tempList!=null){
                        tablePile curTab=(tablePile) e.getSource();
                        if (curTab.isCanAdd(tempList)){
//                            System.out.println("可以移入，设置toTab");
                            toTab = curTab;
                        }
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
//                    System.out.println("方块"+ finalTabindex +"鼠标移出事件");
                    toTab = null;
                }
                @Override
                public void mousePressed(MouseEvent e) {

                    if(e.getButton()!=1)
                        return;
//                    System.out.println("方块"+ finalTabindex +"鼠标点击事件");
                    tablePile curTab=(tablePile) e.getSource();
                    fromTab = curTab;
                    dragFlag = true;
                    pressX = e.getX();
                    pressY = e.getY();
                    tempList = curTab.checkXY(e.getX(), e.getY());//点击后把点击处往下的所有可移动卡牌加入templist
//                    System.out.println("此时移出方块内有"+fromTab.mySize()+"张牌");
                }
                @Override
                public void mouseReleased(MouseEvent e) {

                    if(e.getButton()!=1)
                        return;

                    if(releaseFlag){
//                        System.out.println("方块"+ finalTabindex +"鼠标释放事件");
                        if(toTab != null){
//                            System.out.println("临时list中有"+tempList.size()+"张牌");
//                            System.out.println("add前："+toTab.mySize());
//                            System.out.println("第一张正面朝上的牌是第"+toTab.fir_up_index()+"张");
                            toTab.addCard(tempList);
//                            System.out.println("加入的这张牌的朝向是"+tempList.get(0).isUpward());
//                            System.out.println("add后："+toTab.mySize());
//                            System.out.println( "第一张正面朝上的牌是第"+toTab.fir_up_index()+"张");
                            toTab.setPileBounds();
                            fromTab.remove(tempList.size());
                            fromTab.reverse();
                            fromTab.setPileBounds();
//                            System.out.println("释放添加工作已完成，此时移出方块内有"+fromTab.mySize()+"张牌"+",移入方块内有"+toTab.mySize()+"张牌");
                            tempList = null;

                        }
                        if(toSuit != null){//因为释放事件是在移除方块上发生的，所以suitpile的移入释放事件要写在tablepile和discarpile中
                            if(toSuit.isCanAdd(tempList)){
                                toSuit.addCard(tempList);
                                fromTab.remove(tempList.size());
                                fromTab.reverse();
                                fromTab.setPileBounds();
                                tempList = null;
                            }
                        }
                        jp.repaint();
                        checkWIN();
//                        System.out.println("重画jp");
                    }
                    releaseFlag=false;
                }
            });

            TablePiles[tabindex].addMouseMotionListener(new MouseAdapter() {
                public void mouseDragged(MouseEvent e) {
                    tablePile curTab=(tablePile) e.getSource();
                    if (tempList != null) {
                        if (dragFlag == true) {
                            dragFlag = false;
                            releaseFlag=true;
//                            System.out.println("方块"+ finalTabindex +"鼠标拖拽事件");
                        }
                    }
                }
            });

        }//给tablepile添加的鼠标监听

        for(int suitindex = 0;suitindex < 4;suitindex++){
            SuitPiles[suitindex].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if(tempList!=null){
                        suitPile curSuit=(suitPile) e.getSource();
                        if (curSuit.isCanAdd(tempList)){
                            toSuit = curSuit;
                        }
                    }
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    toTab = null;
                }
            });


        }//给suitpile添加的鼠标监听

        DeckPile.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
//                System.out.println("发牌堆点击事件");
                if(e.getButton()!=1)
                    return;
                if(DeckPile.mySize() == 0){
                    ArrayList<Card> All = DiscardPile.list;
                    for(int count=0;count<All.size();count++){
                        All.get(count).setUpwards(false);
                    }
                    DeckPile.addCard(All);
                    DiscardPile.remove(DiscardPile.mySize());
                    jp.repaint();
                }
                else{
                    Card go = DeckPile.top();
                    go.setUpwards(true);
                    DiscardPile.addCard(go);
                    DeckPile.remove(1);
                    jp.repaint();
                }
            }
        });

        DiscardPile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                /*if(tempList!=null){
                    tablePile curTab=(tablePile) e.getSource();
                    if (curTab.isCanAdd(tempList)){
                        toTab = curTab;
                    }
                }*/
            }
            @Override
            public void mouseExited(MouseEvent e) {
                toTab = null;
            }
            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("弃牌堆张数："+DiscardPile.mySize());
                if(e.getButton()!=1)
                    return;
//                pressX = e.getX();
//                pressY = e.getY();
                if(DiscardPile.mySize() != 0){
                    dragFlag = true;
                    tempList = new ArrayList<Card>();
                    tempList.add(DiscardPile.top());
                }
            }
                // System.out.println(e.getX());

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.getButton()!=1)
                    return;
                if(releaseFlag){
                    if(toTab != null){//来自discarPile，释放于tablepile
                        toTab.addCard(tempList);
                        toTab.setPileBounds();
                        DiscardPile.remove(tempList.size());
                        tempList = null;
                    }
                    if(toSuit != null){//来自discardpile，释放于suitpile
                        if(toSuit.isCanAdd(tempList)){
                            toSuit.addCard(tempList);
                            DiscardPile.remove(tempList.size());
                            tempList = null;
                        }
                    }
//                    System.out.println("discardpile释放事件");
                    jp.repaint();
                    checkWIN();
                }
                releaseFlag=false;
        }
    });

        DiscardPile.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (tempList != null) {
                    if (dragFlag == true) {
                        dragFlag = false;
                        releaseFlag=true;
                    }
                }
            }
        });
}
}


