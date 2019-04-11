package com.abdullahsoubeih.usingretrofitwihsql.RecyclerViewWithApi.ScrollTextView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.abdullahsoubeih.usingretrofitwihsql.R;

public class ScrollingTextViewActivity extends AppCompatActivity {

    TextView scrollingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_text_view);

        scrollingText = (TextView)findViewById(R.id.scrollingtext);
        scrollingText.setSelected(true);
    }
}
