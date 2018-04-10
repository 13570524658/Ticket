package com.future.zhh.ticket.presentation.test;

/**
 * Created by Administrator on 2017/11/19.
 */

public class test {
    public static void main(String[] args) {
        String data = "ddd"+"@|@"+"djfkd"+"@|@"+"dkfjdfdjkfj";
        String ddd[] = data.split("@\\|@", -1);
//        HashMap<Integer,List<Integer>> testHashMap = new HashMap<>();
//        for(int i=0;i<10;i++){
//            testHashMap.put(i,new ArrayList<>());
//        }
        for(int i=0;i<ddd.length;i++)
            System.out.println(Integer.valueOf("100"));
    }
}
