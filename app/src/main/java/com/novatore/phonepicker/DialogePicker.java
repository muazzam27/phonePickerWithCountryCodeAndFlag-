package com.novatore.phonepicker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;



/**
 * Created by macbookpro on 3/25/16.
 */
public class DialogePicker extends Dialog implements AdapterView.OnItemClickListener{

    private CountriesFetcher.CountryList mCountries;
    TextView tit;

    Context c;
    ListView lv;
    DialogeInterfaceCountry countryInterface;

    public DialogePicker(Context context, String title, CountriesFetcher.CountryList Countries , DialogeInterfaceCountry countryInterface) {
        super(context, R.style.dialog_style_simple_two);
        setContentView(R.layout.dialoge_city_list);


        Rect displayRectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.gravity= Gravity.BOTTOM;
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = (int)(displayRectangle.height() * 0.5f);
        getWindow().setAttributes(lp);

        lv=(ListView)findViewById(R.id.listviewcities);
        lv.setOnItemClickListener(this);
        tit=(TextView)findViewById(R.id.textView_title_header);
        tit.setText(title);
        this.countryInterface=countryInterface;
        c=context;
        mCountries=Countries;

        FlagAdapter adapter=new FlagAdapter(c,R.layout.flag_item,mCountries);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public interface DialogeInterfaceCountry{

        public void onCountryItemSelectedSelected(Country countryObject);


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Country  c=mCountries.get(position);
        countryInterface.onCountryItemSelectedSelected(c);
        this.dismiss();

    }
}
