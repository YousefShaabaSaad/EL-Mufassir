package com.yousef.el_mufassir.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sdsmdg.tastytoast.TastyToast;
import com.yousef.el_mufassir.R;
import com.yousef.el_mufassir.activity.AyatActivity;
import com.yousef.el_mufassir.adapter.SouraAdapter;
import com.yousef.el_mufassir.databse.Repository;
import com.yousef.el_mufassir.interfaces.RecyclerViewListener;
import com.yousef.el_mufassir.model.Constants;

public class HomeFragment extends Fragment implements RecyclerViewListener {

    private Repository repository;
    private Constants constants;
    private  SouraAdapter souraAdapter;
    private GridLayoutManager gridLayoutManager;
    private int numOfOpenSoura;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository=new Repository(getContext());
        constants=Constants.newInstance();

        numOfOpenSoura=repository.returnInt( constants.NUM_OF_OPEN_SOURA ,0);

        souraAdapter = new SouraAdapter(  repository.getName(), repository.getAyat(), numOfOpenSoura, this );
        gridLayoutManager = new GridLayoutManager( getContext(), 2 );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewSoura = view.findViewById(R.id.recyclerViewSoura);
        recyclerViewSoura.setLayoutManager(gridLayoutManager);
        recyclerViewSoura.setAdapter(souraAdapter);
    }


    @Override
    public void onClickItem(int visible, int position) {
        int numOfOpenAya=repository.returnInt( constants.NUM_OF_OPEN_AYA ,0);
        Toast.makeText(getContext(),numOfOpenAya+"",Toast.LENGTH_LONG).show();
        if(visible==View.VISIBLE){
            TastyToast.makeText( getContext(), getString(R.string.noOpenSoura)+" "+repository.getName()[position],TastyToast.LENGTH_LONG,TastyToast.ERROR ).show();
        }
        else {
            Intent intent = new Intent(getContext(), AyatActivity.class);
            intent.putExtra(constants.NUMBER, position);
            if( numOfOpenSoura == (position+1) )
                intent.putExtra(constants.LOCK, numOfOpenAya);
            else
                intent.putExtra(constants.LOCK, Integer.parseInt( getResources().getStringArray(R.array.Numbers)[position]));
            startActivity(intent);
        }
    }
}