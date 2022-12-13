package com.example.lab2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.ClassConection;
import com.example.lab2.Db.AppDb;
import com.example.lab2.Db.Recept;
import com.example.lab2.Db.ReceptDao;
import com.example.lab2.MyAdapter;
import com.example.lab2.Recips;
import com.example.lab2.R;
import com.example.lab2.databinding.FragmentHomeBinding;
import com.example.lab2.ui.dashboard.DashboardFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeFragment extends Fragment {

    private List<String> recipsNames = new ArrayList<>();
    private FragmentHomeBinding binding;
    private List<Recips> newArrayList;
    private RecyclerView recyclerView;
    List<Recept> receptList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateInitialize();

        recyclerView = view.findViewById(R.id.RecyclerID);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        MyAdapter myadapter = new MyAdapter(getContext(), receptList);
        recyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }

    private void dateInitialize() {
        AppDb db = AppDb.getInstance(this.getContext());
        newArrayList = new ArrayList<>();
       recipsNames = getPosts();
        receptList = db.receptDao().getAll();
        for(int i = 0; i < receptList.size(); i++) {
            Recips recips = new Recips(receptList.get(i).title, receptList.get(i).description);
            newArrayList.add(recips);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private List getPosts(){
        AppDb db = AppDb.getInstance(this.getContext());

    	ReceptDao receptDao = db.receptDao();
	    Recept dbRecept = new Recept();
        ClassConection connection = new ClassConection();
       try{
           String response = connection.execute("https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/recipes2022.json").get();
       //Format JSON
           JSONArray jsonArray = new JSONArray(response);

           List<String> recipsNames = new ArrayList<String>();

           for(int i = 0; i < jsonArray.length(); i++) {
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               String receptName = jsonObject.getString("Name");
               String receptDescription = jsonObject.getString("Ingredients");
               dbRecept.title = receptName;
               dbRecept.description = receptDescription;
               receptDao.insert(dbRecept);
               recipsNames.add(receptName);
           }

           receptList = db.receptDao().getAll();


           return recipsNames;

       }catch(InterruptedException e){
           e.printStackTrace();
       }catch(ExecutionException e){
           e.printStackTrace();
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return null;
    }

}