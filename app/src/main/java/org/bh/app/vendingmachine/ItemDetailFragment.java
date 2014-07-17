package org.bh.app.vendingmachine;

import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.bh.app.vendingmachine.dummy.Vendibles;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "org.bh.app.vendingmachine.item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Vendibles.Vendible mItem;

    private Toast dispenseToast, couldNotDispenseToast;
    private Button dispenseButton;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Vendibles.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.name);
            dispenseToast = Toast.makeText(
                    getActivity(),
                    "Dispensed 1 " + mItem.name + "!",
                    Toast.LENGTH_SHORT);
            dispenseToast.setGravity(Gravity.CENTER, 0, 0);
            couldNotDispenseToast = Toast.makeText(
                    getActivity(),
                    "Could not dispense " + mItem.name + "!",
                    Toast.LENGTH_SHORT);
            couldNotDispenseToast.setGravity(Gravity.CENTER, 0, 0);
            updateCount(rootView);
        }

        dispenseButton = (Button) rootView.findViewById(R.id.dispense_button);
        dispenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItem instanceof Vendibles.CondensedVendible) {
                    byte oldCount = ((Vendibles.CondensedVendible) mItem).getCount();
                    byte newCount = ((Vendibles.CondensedVendible) mItem).decrement();
                    if (oldCount != newCount)
                        dispenseToast.show();
                    else
                        couldNotDispenseToast.show();
                }
                updateCount(rootView);
            }
        });

        return rootView;
    }

    public void updateCount(View rootView)
    {
        ((TextView)rootView.findViewById(R.id.item_count))
                .setText(
                        (mItem instanceof Vendibles.CondensedVendible
                                ? ((Vendibles.CondensedVendible) mItem).getCount()
                                : 1
                        ) + " left"
                );
    }
}
