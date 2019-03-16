package com.example.gridviewtest;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private int previousSelectedPosition = -1;

    private static final int min = 1;
    private static final int max = 9;

    private static int[] colors = {1,1,1,1,1,1,1,1,1};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the widgets reference from XML Layout
        final GridView gv = (GridView)findViewById(R.id.gv);
        final TextView tv = (TextView)findViewById(R.id.tv_message);

        //Initializing a new string array
        String[] plants = new String[]
                {
                  "Catalina ironwood", "Cabinet cherry", "Pale corydalis", "Pink corydalis", "Belle Isle cress", "Land cress", "Orange coneflower", "Coast polypody", "Water fern"
                };

        //Populate list from array elements
        final List<String> plantsList = new ArrayList<String>(Arrays.asList(plants));

        //Data bind GridView with ArrayAdapter
        gv.setAdapter(new ArrayAdapter<String>
                              (
                        this, android.R.layout.simple_list_item_1, plantsList)
                                {
                                  public View getView(int position, View convertView, ViewGroup parent)
                                  {
                                      View view = super.getView(position, convertView, parent);

                                      TextView tv = (TextView)view;

                                      tv.setTextColor(Color.WHITE);
                                      RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                                      tv.setLayoutParams(lp);

                                      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)tv.getLayoutParams();

                                      params.width = getPixelsFromDPs(MainActivity.this, 168);

                                      tv.setLayoutParams(params);

                                      tv.setGravity(Gravity.CENTER);

                                      tv.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
                                      tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);

                                      tv.setText(plantsList.get(position));

                                      int randomIndex = getRandomIndex();
                                      colors[position] = randomIndex;

                                      tv.setBackgroundColor(getResources().getColor(getColorAccordingToIndex(randomIndex)));

                                      return tv;
                                  }
                              }
                        );

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text
                String selectedItem = parent.getItemAtPosition(position).toString();

                // Display the selected item text to app interface
                tv.setText("Selected item : " + selectedItem);

                // Get the current selected view as a TextView
                TextView tv = (TextView) view;

                // Set the current selected item background color
                tv.setBackgroundColor(Color.parseColor("#FF9AD082"));

                // Set the current selected item text color
                tv.setTextColor(Color.DKGRAY);

                // Get the last selected View from GridView
                TextView previousSelectedView = (TextView) gv.getChildAt(previousSelectedPosition);

                // If there is a previous selected view exists
                if (previousSelectedPosition != -1)
                {
                    // Set the last selected View to deselect
                    previousSelectedView.setSelected(false);

                    // Set the last selected View background color as deselected item
//                    previousSelectedView.setBackgroundColor(Color.parseColor("#FFFF4F25"));
                    previousSelectedView.setBackgroundColor(getResources().getColor(getColorAccordingToIndex(colors[previousSelectedPosition])));

                    // Set the last selected View text color as deselected item
                    previousSelectedView.setTextColor(Color.WHITE);
                }

                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;
            }
        });

    }

    public static int getPixelsFromDPs(Activity activity, int dps)
    {
        Resources r = activity.getResources();
        int px = (int)(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()
        ));
        return px;
    }

    public static int getColorAccordingToIndex(int index)
    {
        switch (index)
        {
            case 1:
                 return R.color.colorYellow;

            case 2:
                return R.color.colorRed;

            case 3:
                return R.color.colorBlue;

            case 4:
                return R.color.colorGreen;

            case 5:
                return R.color.colorBrown;

            case 6:
                return R.color.colorDarkGreen;

            case 7:
                return R.color.colorSkyBlue;

            case 8:
                return R.color.colorMaroon;

                default:
                    return R.color.colorPurple;
        }
    }

    public static int getRandomIndex()
    {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static void setBaseColorOfGridView(int index, int colorIndex)
    {
        if(colors.length > index && index >= 0)
        {
            colors[index] = colorIndex;
        }
    }

    public static int getBaseColorOfGridView(int index)
    {
        if(index < colors.length && index >= 0)
        {
            return colors[index];
        }
        else
        {
            return 1;
        }
    }
}
