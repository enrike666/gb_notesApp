package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static NoteFragment newInstance(Note note) {
        NoteFragment f = new NoteFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        // Установить дату создания заметки
        TextView dateView = view.findViewById(R.id.date);
        dateView.setText(note.getCreateDate());
        return view;
    }
}