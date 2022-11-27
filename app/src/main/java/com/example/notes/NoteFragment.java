package com.example.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class NoteFragment extends Fragment {

    static final String ARG_INDEX = "index";
    static final String SELECTED_NOTE = "note";
    private Note note;


    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();

        Button buttonBack = view.findViewById(R.id.btnBack);
        if (buttonBack != null)
            buttonBack.setOnClickListener(view1 -> {
                requireActivity().getSupportFragmentManager().popBackStack();
            });

        if (arguments != null) {
            //int index = arguments.getInt(ARG_INDEX);
            //note = (Note)arguments.getSerializable(SELECTED_NOTE);
            note = arguments.getParcelable(SELECTED_NOTE);

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            //tvTitle.setText(Note.getNotes()[index].getTitle());
            tvTitle.setText(note.getTitle());
            tvTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setTitle(charSequence.toString());
                    // Note.getNotes()[index].setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {}
            });

            TextView tvDescription = view.findViewById(R.id.tvDescription);
            //tvDescription.setText(Note.getNotes()[index].getDescription());
            tvDescription.setText(note.getDescription());
        }
    }

    public static NoteFragment newInstance(int index) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_NOTE, note);
        //args.putSerializable(SELECTED_NOTE, note);
        //args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

}