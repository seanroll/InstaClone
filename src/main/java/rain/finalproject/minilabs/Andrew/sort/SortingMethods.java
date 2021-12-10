package rain.finalproject.minilabs.Andrew.sort;

import net.bytebuddy.TypeCache;

import java.util.ArrayList;



public class SortingMethods {



    public Object[] insertList;
    public double insertTime;

    public Object[] BubbleList;
    public double BubbleTime;

    public Object[] SelectList;
    public double SelectionTime;


    public Object lists[];


    public SortingMethods(Object list[]) {
        this.lists = list;

        long Start = System.nanoTime();
        insertList = insertSort(list.clone());
        long End = System.nanoTime();
        insertTime = ((End - Start) / 1000000.0);

        Start = System.nanoTime();
        BubbleList = BubbleSort(list.clone());
        End = System.nanoTime();
        BubbleTime = ((End - Start) / 1000000.0);

        Start = System.nanoTime();
        SelectList = SelectionSort(list);
        End = System.nanoTime();
        SelectionTime = ((End - Start) / 1000000.0);


    }


    public Object[] insertSort(Object list[]) {

        for (int i = 0; i < list.length; i++) {
            Object key = list[i];
            int j = i - 1;
            while (j >= 0 && ((list[j].toString().compareTo(key.toString()) > 0))) {
                list[j + 1] = list[j];
                j--;
            }
            list[j + 1] = key;
        }
        return list;
    }


    public Object[] BubbleSort(Object list[]) {


        for (int i = 0; i < list.length - 1; i++) {
            for (int j = 0; j < list.length - i - 1; i++) {
                if ((list[i + 1].toString().compareTo(list[i].toString())) > 0) {
                    Object temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
        return list;
    }

    public Object[] SelectionSort(Object list[]) {

        for (int i = 0; i < list.length - 1; i++) {
            int tempindex = i;
            for (int j = i + 1; j < list.length; j++)
                if (list[j].toString().compareTo(list[tempindex].toString()) < 0)
                    tempindex = j;



            Object temp = list[tempindex];
            list[tempindex] = list[i];
            list[i] = temp;
        }
        return list;
    }



}
