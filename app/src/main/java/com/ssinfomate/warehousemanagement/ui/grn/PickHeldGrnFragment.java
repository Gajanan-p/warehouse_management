package com.ssinfomate.warehousemanagement.ui.grn;



import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.webservices.grn.HeldGrnListModel;

public class PickHeldGrnFragment extends Fragment {

    private HeldGrnListModel mViewModel;

    public static PickHeldGrnFragment newInstance() {
        return new PickHeldGrnFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pick_held_grn_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(HeldGrnListModel.class);
        // TODO: Use the ViewModel
    }

}