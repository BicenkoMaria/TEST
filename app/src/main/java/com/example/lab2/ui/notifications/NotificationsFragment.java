package com.example.lab2.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.Db.AppDb;
import com.example.lab2.Db.FavoriteRecepts;
import com.example.lab2.Db.Recept;
import com.example.lab2.Db.User;
import com.example.lab2.MainActivity2;
import com.example.lab2.MyAdapter;
import com.example.lab2.R;
import com.example.lab2.ReceptModel;
import com.example.lab2.Recips;
import com.example.lab2.databinding.FragmentNotificationsBinding;
import com.example.lab2.ui.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    List<Recept> receptList = new ArrayList<Recept>();
    User user;
    List<FavoriteRecepts> favoriteRecepts;
    private RecyclerView mRecyclerView;
    String userEmail = MainActivity2.userEmail;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

      //  final TextView textView = binding.textNotifications;
      //  notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDb db = AppDb.getInstance(this.getContext());

        user = db.userDao().getUserByEmail(userEmail);

        favoriteRecepts = db.FavoriteReceptsDao().getUserFavoriteReceptsById(user.id);

        receptList.clear();

        for(int i = 0; i < favoriteRecepts.size(); i++) {
            Recept recept = db.receptDao().getReceptById(favoriteRecepts.get(i).receptId);
            if (recept != null) {
                receptList.add(recept);
            }
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.Recycler2ID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        if (receptList == null) {
            return;
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        MyAdapter myadapter = new MyAdapter(getContext(), receptList);

        mRecyclerView.setAdapter(myadapter);
        myadapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}