package org.bh.app.vendingmachine.data_structure;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.bh.app.vendingmachine.R;

import java.util.List;
import java.util.Map;

/**
 * Made for Vending Machine by and copyrighted to Blue Husky Programming, ©2014 GPLv3.<hr/>
 *
 * @author Kyli Rouge of Blue Husky Programming
 * @version 1.0.0
 * @since 2014-07-17
 */
public class VendableItemListAdapter extends BaseAdapter {
    private Activity activity;
    private List<Map<String, String>> data;
    private static LayoutInflater inflater = null;

    public VendableItemListAdapter(Activity initActivity, List<Map<String, String>> initData)
    {
        activity = initActivity;
        data = initData;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.vendable_list_item, null);
        TextView line1 = (TextView)vi.findViewById(R.id.list_item_line_1);
        TextView line2 = (TextView)vi.findViewById(R.id.list_item_line_2);
        ImageView icon = (ImageView)vi.findViewById(R.id.list_item_icon);

        Map<String, String> vendible = data.get(position);

        line1.setText(vendible.get(Vendibles.NAME_KEY));
        line2.setText(vendible.get(Vendibles.COUNT_KEY));
        icon.setImageResource(Integer.parseInt(vendible.get(Vendibles.ICON_KEY)));

        return vi;
    }
}
