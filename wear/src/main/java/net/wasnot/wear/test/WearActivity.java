package net.wasnot.wear.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class WearActivity extends Activity implements View.OnClickListener {

    public EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mEditText = (EditText) stub.findViewById(R.id.editText);

                stub.findViewById(R.id.button1).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button2).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button3).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button4).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button5).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button6).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button7).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button8).setOnClickListener(WearActivity.this);
                stub.findViewById(R.id.button9).setOnClickListener(WearActivity.this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        mEditText.append(((TextView) v).getText());
    }


}
