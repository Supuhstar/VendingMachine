package org.bh.app.vendingmachine.data_structure;

import org.bh.app.vendingmachine.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing vendibles
 */
public class Vendibles {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Vendible> ITEMS = new ArrayList<Vendible>();

    /**
     * A map of vendable items
     */
    public static Map<String, Vendible> ITEM_MAP = new HashMap<String, Vendible>();

    static {
        // Add 3 sample items.
        reloadItems();
    }

    public static void reloadItems()
    {
        ITEMS.clear();
        ITEM_MAP.clear();

        addItem("Pepsi",   R.drawable.icon_pepsi,   (byte)10);
        addItem("KitKat",  R.drawable.icon_kitkat,  (byte)10);
        addItem("Doritos", R.drawable.icon_doritos, (byte)10);
        addItem("Water",   R.drawable.icon_dasani,  (byte)10);
    }

    public static void addItem(String itemName, int initIconID, byte count) {
        Vendible item = new CondensedVendible(itemName, initIconID, count);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static byte getCount(String itemName)
    {
        byte counter = 0;
        for(Vendible v : ITEMS)
            if (v.name == itemName)
                if (v instanceof CondensedVendible)
                    counter += ((CondensedVendible) v).getCount();
                else
                    counter++;
        return counter;
    }

    public static List<Vendible> getExpandedVendibles()
    {
        List<Vendible> ret = new ArrayList<Vendible>();
        for(Vendible v : ITEMS)
            if (v instanceof CondensedVendible)
                ret.addAll(((CondensedVendible) v).expand());
            else
                ret.add(v);
        return ret;
    }

    public static final String COUNT_KEY = "count", NAME_KEY = "name", ICON_KEY = "icon";
    public static List<Map<String, String>> toMapList()
    {
        List<Map<String, String>> ret = new ArrayList<Map<String, String>>();
        for(Vendible v : ITEMS) {
            if (v == null) continue; // no use trying to process notyhing
            Map<String, String> uselesslySmallMap = new HashMap<String, String>();
            uselesslySmallMap.put(COUNT_KEY,
                (v instanceof CondensedVendible
                    ? ((CondensedVendible) v).count
                    : (byte)1
                )+" left"
            );
            uselesslySmallMap.put(NAME_KEY, v.name);
            uselesslySmallMap.put(ICON_KEY, v.iconID+"");

            ret.add(uselesslySmallMap);
        }
        return ret;
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Vendible {
        public String id;
        public String name;
        public int iconID;

        public Vendible(String initName, int initIconID) {
            this.id = IDGen.newID();
            name = initName;
            iconID = initIconID;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class CondensedVendible extends Vendible
    {
        private byte count = 0;
        public CondensedVendible(String initName, int initIconID, byte initCount)
        {
            super(initName, initIconID);
            count = initCount;
        }

        public byte setCount(byte newCount)
        {
            return newCount >= 0 ? (count = newCount) : count;
        }

        public byte getCount()
        {
            return count;
        }

        public byte decrement()
        {
            return count > 0 ? --count : count;
        }

        public byte increment()
        {
            return count++;
        }

        public List<Vendible> expand()
        {
            List<Vendible> ret = new ArrayList<Vendible>();
            for (int i = 0; i < count; i++)
                ret.add(new Vendible(name, iconID));
            return ret;
        }

        @Override
        public String toString() {
            return "[" + count + "] " + super.toString();
        }
    }

    public static class IDGen
    {
        private static long idCounter = 0;

        private IDGen(){}

        public static String newID() {
            return idCounter+++"";
        }
    }
}
