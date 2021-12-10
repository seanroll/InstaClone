package rain.finalproject.minilabs.Jason;

import java.util.ArrayList;
import java.lang.Math;

public class Sort{

    public static int[] InsertionSortInt(int[] sort){
        int n = sort.length;
        for (int i = 1; i < n; ++i) {
            int save = sort[i];
            int j = i - 1;
            while (j >= 0 && sort[j] > save) {
                sort[j + 1] = sort[j];
                j = j - 1;
            }
            sort[j + 1] = save;
        }
        return sort;

    }
    public static String[] InsertionSortString(String[] sort){
        int n = sort.length;
        for (int i = 1; i < n; ++i) {
            String save = sort[i];
            int j = i - 1;
            while (j >= 0 && sort[j].length() > save.length()) {
                sort[j + 1] = sort[j];
                j = j - 1;
            }
            sort[j + 1] = save;
        }
        return sort;
    }

    public static int[] SelectionSortInt(int[] sort){
        int n = sort.length;

        for (int i = 0; i < n-1; i++)
        {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (sort[j] < sort[min])
                    min = j;
            int temp = sort[min];
            sort[min] = sort[i];
            sort[i] = temp;
        }
        return sort;
    }
    public static String[] SelectionSortString(String[] sort){
        int n = sort.length;

        for (int i = 0; i < n-1; i++)
        {
            int min = i;
            for (int j = i+1; j < n; j++)
                if (sort[j].length() < sort[min].length())
                    min = j;
            String temp = sort[min];
            sort[min] = sort[i];
            sort[i] = temp;
        }
        return sort;
    }


    public static int[] BubbleSortInt(int[] sort){
        int n = sort.length;
        for (int i = 0; i < n-1; i++)
        {
            for (int j = 0; j < n-i-1; j++)
            {
                if (sort[j] > sort[j+1])
                {
                    int temp = sort[j];
                    sort[j] = sort[j+1];
                    sort[j+1] = temp;
                }
            }
        }
        return sort;
    }
    public static String[] BubbleSortString(String[] sort){
        int n = sort.length;
        for (int i = 0; i < n-1; i++)
        {
            for (int j = 0; j < n-i-1; j++)
            {
                if (sort[j].length() > sort[j+1].length())
                {
                    String temp = sort[j];
                    sort[j] = sort[j+1];
                    sort[j+1] = temp;
                }
            }
        }
        return sort;
    }
    public static double GetFunctionRunTime(Runnable Function) {
        long Start = System.nanoTime();
        Function.run();
        return (System.nanoTime() - Start) / 1000000.0;
    }

}