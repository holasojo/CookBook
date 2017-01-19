package com.cs3714.sojo.proj;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs3714.sojo.proj.Fragments.RetainedFragment;
import com.cs3714.sojo.proj.Objects.Result;
import com.cs3714.sojo.proj.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
//author1 : Jeongho Noh
//author2 : so hyun jo

//main class for the cook book this activity hold fragments to search recipe

public class MainActivity extends Activity implements ActivityInTheRFFragmentInterface {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private ActionTabsViewPagerAdapter myViewPageAdapter;
    private ActionBar actionBar;
    public List<Result> curators;
    EditText sear;
    ArrayAdapter<String> myAdapter;
    ListView listView;
    String[] dataArray = new String[]{"butter", "mayonnaise", "apple", "rice", "lemon", "banana", "egg", "salmon", "sugar",
            "peach", "pasta", "tomato", "oil", "cheese", "tuna", "ketchup", "bread", "onion", "pepper", "cream", "chicken", "milk"
            , "salt", "garlic", "syrup", "flour", "soy sauce", "sausage", "tofu", "almond", "bean", "cherry", "pork", "beef"};
    private FragmentManager fragMgr;
    private RetainedFragment retainedFrag;
    private static final String TAG_RECIPE_PAGE = "Recipe_Page";
    FindFragment recipeList;
    private static final String TAG_RETAINED = "Retained";
    public ArrayList<String> lists;
    TextView tv;
    MeetFragment mf;
    ChatFragment cf;
    Typeface tf;
    Ingredients ingDB;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        fragMgr = getFragmentManager();
        connectWithRetainedFragment();
        ingDB = new Ingredients(this);

        List<Ingredient> ingList;
        ingList = ingDB.getAllIngredients();
        // add all the elements into the database if there is none in the database.

        if (ingList == null) {
            for (int j = 0; j < dataArray.length; j++) {
                ingDB.addIngredient(new Ingredient(dataArray[j], ""));
            }
        }
        //put all the elemtns to the list

        ingDB.addIngredient(new Ingredient("Grapefruit", ""));
        List<String> list = ingDB.getAllNames();
//


        // Define SlidingTabLayout (shown at top)
        // and ViewPager (shown at bottom) in the layout.
        // Get their instances.
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        listView = (ListView) findViewById(R.id.listview2);
        registerForContextMenu(listView);
        sear = (EditText) findViewById(R.id.menuitem_search);
        myAdapter = new ArrayAdapter<>(this, R.layout.recipelistentrylaout, list);
        recipeList = new FindFragment();
        mf = new MeetFragment();
        cf = new ChatFragment();
        listView.setAdapter(myAdapter);
        listView.setTextFilterEnabled(true);
        listView.setVisibility(View.INVISIBLE);

        lists = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //when user click the ingrements it will add the list of added ingrements and from this list
            //application will look for the recipe
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String name = myAdapter.getItem(arg2);
                Toast.makeText(getApplicationContext(), name + " added", Toast.LENGTH_SHORT).show();
                System.out.println(arg2 + " --postion");
                if (!lists.contains(name)) {
                    lists.add(name);
                } else {
                    Toast.makeText(getApplicationContext(), name + " already in the list", Toast.LENGTH_SHORT).show();

                }

                System.out.println("list = ");


                retainedFrag.runIt(lists);
            }

        });


        // create a fragment list in order.
        fragments = new ArrayList<Fragment>();
        fragments.add(cf);
        fragments.add(recipeList);
        fragments.add(mf);

        // use FragmentPagerAdapter to bind the slidingTabLayout (tabs with different titles)
        // and ViewPager (different pages of fragment) together.
        myViewPageAdapter = new ActionTabsViewPagerAdapter(getFragmentManager(),
                fragments);
        viewPager.setAdapter(myViewPageAdapter);

        // make sure the tabs are equally spaced.
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        actionBar = getActionBar();

        if (savedInstanceState != null) {
            lists = savedInstanceState.getStringArrayList("num");
        }


    }

    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("num", lists);
    }

    private void connectWithRetainedFragment() {

        retainedFrag = (RetainedFragment) fragMgr.findFragmentByTag(TAG_RETAINED);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (retainedFrag == null) {
            retainedFrag = new RetainedFragment();
            fragMgr.beginTransaction().add(retainedFrag, TAG_RETAINED).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuitem_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                listView.setVisibility(View.VISIBLE);
                myAdapter.getFilter().filter(newText);
                System.out.println("on text chnge text: " + newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                myAdapter.getFilter().filter(query);
                System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.menuitem_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                listView.setVisibility(View.INVISIBLE);
                slidingTabLayout.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.VISIBLE);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);


    }

// when user click the menu buttons it will choose the fragment

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_search:
                Toast.makeText(this, getString(R.string.ui_menu_search),
                        Toast.LENGTH_SHORT).show();
                listView.setVisibility(View.VISIBLE);
                slidingTabLayout.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.INVISIBLE);
                return true;


            case R.id.menuitem_quit:
                Toast.makeText(this, getString(R.string.ui_menu_quit),
                        Toast.LENGTH_SHORT).show();
                finish(); // close the activity
                return true;
        }
        return false;
    }

// it will give the list to activity so that we can follow the added list

    public List<String> getList() {
        if (lists != null) {
            return lists;
        }
        return null;
    }


    public void onBackPressed() {
        if (fragMgr.getBackStackEntryCount() != 0) {
            fragMgr.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // start the detail screen frament for the show the user that detail recipe

    public void showDetailRecipe() {

        mf.startTask();

    }

    public void sendResults(List<Result> it) {
        curators = it;
        Log.d("in send Results",
                "");

        if (recipeList != null) {
            Log.d("in send Results",
                    "recipe_list");
            (recipeList).updateContent(curators);
        }
    }

    public void updateList(List<String> list) {
        retainedFrag.runIt((ArrayList<String>) list);
    }

    public boolean doneProcessing(boolean boo) {
        return boo;
    }


    public List<Result> getData() {
        return curators;
    }
}