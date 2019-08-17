package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ClassroomFragment extends Fragment {
    ListView List;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classroom , container,false);
        ArrayList<String> ArrList=new ArrayList<>();

        /**
         * here we must search about the university and the year to get the subjects of this student
         * and add the subjects here in the array list
         **/
        ArrList.add("Material 1");
        ArrList.add("Material 2");
        ArrList.add("Material 3");
        ArrList.add("Material 4");
        ArrList.add("Material 5");
        ArrList.add("Material 6");
        ArrayAdapter<String> items= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,ArrList);
        List=(ListView)view.findViewById(R.id.List);
        List.setAdapter(items);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /** here we must know the material name that the student click on it cause we 'll search about it in the next fragment **/
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SubjectFragment() ).commit();
            }
        });
        return view;
    }
}
