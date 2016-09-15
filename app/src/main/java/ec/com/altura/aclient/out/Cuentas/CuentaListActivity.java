package ec.com.altura.aclient.out.Cuentas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ec.com.altura.aclient.util.Me;
import systems.altura.util.layout.base.Adpater;

import ec.com.altura.aclient.R;




public class CuentaListActivity extends AppCompatActivity {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_list);



        ListView listView = (ListView)findViewById(R.id.cuenta_list);
        assert listView != null;

        Adapter adapter = new Adpater(listView.getContext(), Me.LIST_MAIN);
        listView.setAdapter((ListAdapter)adapter);

        if (findViewById(R.id.cuenta_detail_container) != null) {
            mTwoPane = true;
        }
    }

}
