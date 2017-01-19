package com.cs3714.sojo.proj;

import com.cs3714.sojo.proj.Objects.Result;

import java.util.List;

/**
 * Created by Andrey on 7/19/2015.
 */
public interface ActivityInTheRFFragmentInterface {


          void sendResults(List<Result> it);
          boolean doneProcessing(boolean boo);
        List<Result> getData();
    void showDetailRecipe();
    List<String> getList();
    void updateList(List<String> list);

}
