package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public  class Pile extends JPanel {

    ArrayList<Card> list;
    ArrayList<Card> getList(){return  list;};
    int X;
    int Y;
    int initSize = 0;
    Image image;

    Pile(int x,int y){
        setVisible(true);
        list = new ArrayList<Card>();
        Color c = new Color(184, 184, 230);
        setBackground(c);
        setBounds(x,y,57,88);
    }

    public Card top() { return list.get(mySize()-1); }

    public void addCard(ArrayList<Card> L) {
        list.addAll(L);
    }

    public void addCard(Card C) {
        list.add(C);
    }

    public int mySize() {
        return list.size();
    }

    public void remove(int n) {
        for(int i=0;i<n;i++){
            list.remove(mySize()-1);
        }
    }

    public void paintComponent(Graphics g) {}
}
