package com.tamimehsan;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        String[] strings = {"abla", "Cfasdf", "efsdfs",};
        String[] strings2 = {"bsdfla", "dfassdf", "fsdfs","gzBla"};
        Array array1 = new Array(strings);
        Array array = new Array(strings2);
       // System.out.println(Arrays.toString(array1.getArray()));
       // System.out.println(Arrays.toString(array1.findIndex("Bla")));
       // array1.remove("Bla");
       // System.out.println(Arrays.toString(array1.getArray()));
       // System.out.println(Arrays.toString(array1.findIndex("Bla")));
        Array array2 = new Array();
        array2.merge(array.getArray(),array1.getArray());
        System.out.println(Arrays.toString(array2.getArray()));
   }
}
