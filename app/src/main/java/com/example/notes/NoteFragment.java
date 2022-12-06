package com.example.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NoteFragment extends Fragment {

    static final String ARG_INDEX = "index";
    static final String SELECTED_NOTE = "note";
    private Note note;
    private Toast deleteToast;


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
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_menu, menu);

        // Меню от активити

        MenuItem menuItem = menu.findItem(R.id.action_about);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    private Toast preparedCustomToast(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.my_toast_layout,
                (ViewGroup) view.findViewById(R.id.toast_layout_root));

        ImageView image1 = layout.findViewById(R.id.image1);
        image1.setImageResource(R.drawable.ic_edit);

        ImageView image2 = layout.findViewById(R.id.image2);
        image2.setImageResource(R.drawable.ic_edit);

        TextView textView = layout.findViewById(R.id.text);
        textView.setText("Заметка удалена 2");

        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        return toast;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {

            new AlertDialog.Builder(getContext())
                    .setTitle("Внимание!")
                    .setMessage("Вы действительно желаете удалить заметку?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Note.getNotes().remove(note);
                            note = null;
                            updateData();
                            if (!isLandscape()) {
                                requireActivity().getSupportFragmentManager().popBackStack();
                            }
                            deleteToast.show();
                        }
                    })
                    .setNegativeButton("Нет", null)
                    .show();

            //Toast.makeText(getContext(), "Заметка удалена 1", Toast.LENGTH_LONG).show();
            //deleteToast.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateData() {
        for (Fragment fragment : requireActivity().getSupportFragmentManager().getFragments()) {
            if (fragment instanceof NotesFragment) {
                ((NotesFragment) fragment).initNotes();
                break;
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deleteToast = preparedCustomToast(view);

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
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setTitle(charSequence.toString());
                    // Note.getNotes()[index].setTitle(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
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


    private boolean isLandscape() {
        return getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

}