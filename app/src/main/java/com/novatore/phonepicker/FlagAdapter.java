package com.novatore.phonepicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by macbookpro on 3/25/16.
 */
public class FlagAdapter extends ArrayAdapter<Country> {

    private Context context;
    private CountriesFetcher.CountryList mCountries;

    public FlagAdapter(Context context, int resource, CountriesFetcher.CountryList mCountries) {
        super(context, resource, mCountries);
        this.context = context;
        this. mCountries = mCountries;
    }

    static class ViewHolder{
        ImageView countryImage;
        TextView countyWithCode;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.flag_item, parent, false);
            holder=new ViewHolder();
            holder.countyWithCode=(TextView)convertView.findViewById(R.id.countryName);
            holder.countryImage=(ImageView)convertView.findViewById(R.id.flagImage);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        Country country = mCountries.get(position);
        Typeface regular = Typeface.createFromAsset(getContext().getAssets(), "fonts/proximanova-regular.ttf");
        holder.countyWithCode.setText(country.getName()+" "+ "+"+country.getDialCode());
        holder.countyWithCode.setTypeface(regular);
        holder.countryImage.setImageResource(getFlagResource(country));



        return convertView;

    }

    /**
     * Fetch flag resource by Country
     *
     * @param country Country
     * @return int of resource | 0 value if not exists
     */
    private int getFlagResource(Country country) {
        return getContext().getResources().getIdentifier("country_" + country.getIso().toLowerCase(), "drawable", getContext().getPackageName());
    }




}
