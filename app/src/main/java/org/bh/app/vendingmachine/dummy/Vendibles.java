package org.bh.app.vendingmachine.dummy;

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

        addItem("Pepsi", (byte) 10);
        addItem("KitKat", (byte)10);
        addItem("Doritos", (byte)10);
        addItem("Water", (byte)10);
    }

    public static void addItem(String itemName, byte count) {
        Vendible item = new CondensedVendible(itemName, count);
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

    /**
     * A dummy item representing a piece of content.
     */
    public static class Vendible {
        public String id;
        public String name;

        public Vendible(String initName) {
            this.id = IDGen.newID();
            name = initName;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static class CondensedVendible extends Vendible
    {
        private byte count = 0;
        public CondensedVendible(String name, byte initCount)
        {
            super(name);
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
                ret.add(new Vendible(name));
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
