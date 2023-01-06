package pl.edu.pb.lepszeduolingo.ui.admin.add;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import pl.edu.pb.lepszeduolingo.R;

public class AddWordTranslationsAdapter extends BaseAdapter{
    Context context;
    LinkedHashMap<String,String> translationsTemp;
    private static LayoutInflater inflater = null;
    List<String> translationsWordsList;
    TextView translationView;
    TextView languageView;
    ImageButton deleteBtn;
    private AddWordTranslationsAdapterListener translationsAdapterListener;

    public AddWordTranslationsAdapter(Context context, LinkedHashMap<String,String> translationsTemp, AddWordTranslationsAdapterListener translationsAdapterListener) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.translationsTemp = translationsTemp;
        this.translationsAdapterListener = translationsAdapterListener;
        this.translationsWordsList = new ArrayList<String>(translationsTemp.keySet());
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return translationsTemp.size();
    }

    @Override
    public Object getItem(int position) {
        return translationsWordsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View listView = convertView;
        if (listView == null){
            listView = inflater.inflate(R.layout.admintranslations_list_view_row, null);
        }
        // views
        translationView = listView.findViewById(R.id.adminTranslationText);
        languageView = listView.findViewById(R.id.adminTranslationLanguage);
        deleteBtn = listView.findViewById(R.id.deleteadminTranslationBtn);
        // set existing
        translationView.setText(translationsWordsList.get(position));
        languageView.setText(translationsTemp.get(translationsWordsList.get(position)));
        // click
        listView.setOnClickListener(v -> handleEdit(position));
        deleteBtn.setOnClickListener(v -> handleDelete(position));
        return listView;
    }
    private void handleDelete(int position){
        String word = translationsWordsList.get(position);
        Log.d("translationsTest", word);
        // remove
        translationsTemp.remove(word);
        translationsWordsList.remove(position);
        this.notifyDataSetChanged();
        // update from adapter
        translationsAdapterListener.updateList(translationsTemp);
    }
    private void handleEdit(int position){
        Log.d("translationsTest", String.valueOf(position+" edit"));
    }
    public void setList(LinkedHashMap<String,String> translationsTemp){
        // update from fragment
        this.translationsTemp = translationsTemp;
        this.translationsWordsList = new ArrayList<String>(translationsTemp.keySet());
    }
    public interface AddWordTranslationsAdapterListener{
        void updateList(LinkedHashMap<String,String> translationsTemp);
    }
}
