package pl.edu.pb.lepszeduolingo.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomUtil {

    public static List<Integer> drawRandomNumbers(int max){
        List<Integer> list = new ArrayList<>();
        List<Integer> outputList = new ArrayList<>();

        for (int i=1; i<max; i++) list.add(i);
        Collections.shuffle(list);
        for (int i=0; i<4; i++){
            outputList.add(list.get(i));
        }
        return outputList;
    }
}
