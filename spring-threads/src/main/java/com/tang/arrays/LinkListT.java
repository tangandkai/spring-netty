package com.tang.arrays;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class LinkListT <E extends Comparable<E>> {

    /**
     * 节点
     */
    private class Node{
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e,null);
        }

        public Node() {
            this(null,null);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "e=" + e +
                    ", next=" + next +
                    '}';
        }
    }
    private Node dumpyHead;
    private int size;

    public LinkListT() {
        dumpyHead = new Node(null,null);
        size = 0;
    }

    /**
     * 获取链表大小
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 判断链表的大小
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 往指定位置添加元素
     * @param index
     * @param e
     */
    public void add(int index,E e){
        if (index<0 || index>size){
            throw new IllegalArgumentException("Add failed . Illegal index");
        }

        else {
            Node pre = dumpyHead;
            for (int i=0;i<index;i++){
                pre = pre.next;
            }
            Node node = new Node(e);
            node.next = pre.next;
            pre.next = node;
//            pre.next = new Node(e,pre.next);
            size ++;
        }
    }


    public void tt(){
        Node loHead = null, loTail = null;
        Node hiHead = null, hiTail = null;
        Node next;
        Node e = dumpyHead;
//        Node<E>[] newTab =  new Node<>[32];
        int j=2;
        int oldCap = 12;
        int i = 0;
        do {
            next = e.next;
            if (i%2 == 0) {
                if (loTail == null)
                    loHead = e;
                else
                    loTail.next = e;
                loTail = e;
            }
            else {
                if (hiTail == null)
                    hiHead = e;
                else
                    hiTail.next = e;
                hiTail = e;
            }
            i++;
        } while ((e = next) != null);

        if (loTail != null) {
            loTail.next = null;
//            newTab[j] = loHead;
        }
        if (hiTail != null) {
            hiTail.next = null;
//            newTab[j + oldCap] = hiHead;
        }
    }

    /**
     * 对链表排序
     * 选择排序
     * flag为true，由小到大排序
     * flag为false，由大到小排序
     * @param flag
     */
    public void order(boolean flag){
        Node curNode = dumpyHead.next;
        Node nextNode;
        if (flag==true){
            while (curNode.next!=null){
                nextNode = curNode.next;
                while (nextNode!=null){
                    if (curNode.e.compareTo(nextNode.e)>0){
                        E tmp = curNode.e;
                        curNode.e = nextNode.e;
                        nextNode.e = tmp;
                    }
                    nextNode = nextNode.next;
                }
                curNode = curNode.next;
            }
        }
        else {
            while (curNode.next!=null){
                nextNode = curNode.next;
                while (nextNode!=null){
                    if (curNode.e.compareTo(nextNode.e)<0){
                        E tmp = curNode.e;
                        curNode.e = nextNode.e;
                        nextNode.e = tmp;
                    }
                    nextNode = nextNode.next;
                }
                curNode = curNode.next;
            }
        }
    }

    /**
     * 删除链表的重复数据
     */
    public void distinct(){
        Hashtable<E,Integer> table = new Hashtable<>();
        Node curNode = dumpyHead;
        while (curNode.next!=null){
            if (table.containsKey(curNode.next.e)){
                Node preNode = curNode;
                preNode.next = curNode.next.next;
                size--;
            }
            else {
                table.put(curNode.next.e,1);
                curNode = curNode.next;
            }

        }
    }
    /**
     * 往头结点添加元素
     * @param e
     */
    public void addFirst(E e){
        this.add(0,e);
    }

    /**
     * 往尾节点添加元素
     * @param e
     */
    public void addLast(E e){
        this.add(size,e);
    }

    /**
     * 获取指定元素
     * @param index
     * @return
     */
    public E get(int index){
        if (index<0 || index>size){
            throw new IllegalArgumentException("Add failed . Illegal index");
        }
        Node cur = dumpyHead.next;
        for (int i=0;i<index;i++){
            cur = cur.next;
        }
        return cur.e;
    }

    public Node getNext(){
        Node c = dumpyHead;
        return c.next;
    }

    /**
     * 修改指定位置的元素
     * @param index
     * @param e
     */
    public void set(int index,E e){
        if (index<0 || index>size){
            throw new IllegalArgumentException("Add failed . Illegal index");
        }
        Node cur = dumpyHead.next;
        for (int i=0;i<index;i++){
            cur = cur.next;
        }
        cur.e = e;
    }

    /**
     * 查找链表中是否包含给定的元素
     * @param e
     * @return
     */
    public boolean contains(E e){
        Node cur = dumpyHead.next;
        while (cur!=null){
            if (cur.e.equals(e)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Node cur = dumpyHead.next;
        while (cur !=null){
            builder.append(cur.e+"-->");
            cur = cur.next;
        }
        builder.append("null");
        return builder.toString();
    }

    /**
     * 删除指定位置元素
     * @param index
     * @return
     */
    public E remove(int index){
        if (index<0 || index>size){
            throw new IllegalArgumentException("Add failed . Illegal index");
        }
        Node pre = dumpyHead.next;
        for (int i=0;i<index;i++){
            pre = pre.next;
        }
        Node cur = pre.next;
        pre.next = cur.next;
        cur.next=null;
        size --;
        return cur.e;
    }

    /**
     * 删除头部元素
     * @return
     */
    public E removeFirst(){
        return remove(0);
    }

    /**
     * 删除尾部元素
     * @return
     */
    public E removeLast(){
        return remove(size-1);
    }

    public static void main(String[] args) {
        LinkListT<Integer> linkedList = new LinkListT<>();
        linkedList.addLast(1);
        linkedList.addLast(5);
        linkedList.addLast(4);
//        linkedList.addLast(3);
//        linkedList.addLast(2);
//        linkedList.addLast(1);
//        linkedList.addLast(1);
        System.out.println(linkedList.dumpyHead.next);

        linkedList.tt();
        System.out.println(linkedList.toString());
        linkedList.order(true);
//        System.out.println(linkedList.toString());
//        linkedList.order(false);
        System.out.println(linkedList.toString());
        linkedList.distinct();
        System.out.println(linkedList.toString());


    }

}
