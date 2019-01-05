package app.com.klexos.android.urbanevolution.urbanfit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.klexos.android.urbanevolution.MainActivity;
import app.com.klexos.android.urbanevolution.R;

public class UrbanFitHome extends Fragment {

    public UrbanFitHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle("Urban Fit Guide");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_urban_fit_home, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
