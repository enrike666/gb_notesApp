package com.example.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesListFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаем список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.names);

        // В этом цикле создаем элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаем обработку касания на элемент
        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(30);
            layoutView.addView(tv);
            final int fi = i;

            int id = getResources().getIntArray(R.array.ids)[fi];
            String name = getResources().getStringArray(R.array.names)[fi];
            String dateCreate = getResources().getStringArray(R.array.dates)[fi];
            String description = getResources().getStringArray(R.array.description)[fi];

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new Note(id, name, dateCreate, description);
                    showNote(currentNote); //метод показа заметки по тапу
                }
            });
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом заметку в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        // Если это не первое создание, то восстановим текущую позицию
        if (savedInstanceState != null) {
            // Восстановление текущей позиции.
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
//             Если воccтановить не удалось, то сделаем объект по умолчанию
            currentNote = new Note(666, "заметка", "еще не создали", "описание");
        }

        // Если можно нарисовать рядом заметку, то сделаем это
        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    // Показать заметку в ландшафтной ориентации
    private void showLandNote(Note currentNote) {
        // Создаем новый фрагмент
        NoteFragment detail = NoteFragment.newInstance(currentNote);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показать заметку в портретной ориентации.
    private void showPortNote(Note currentNote) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        // и передадим туда параметры
        intent.putExtra(NoteFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }
}