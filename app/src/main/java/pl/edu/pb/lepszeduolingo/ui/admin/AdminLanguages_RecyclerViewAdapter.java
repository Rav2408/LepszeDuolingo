package pl.edu.pb.lepszeduolingo.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pb.lepszeduolingo.R;

public class AdminLanguages_RecyclerViewAdapter  extends RecyclerView.Adapter<AdminLanguages_RecyclerViewAdapter.ViewHolder>{
    private AdminLanguages_RecyclerViewAdapter.onDataListener onDataListener;
    ArrayList<String> languagesData;
    public AdminLanguages_RecyclerViewAdapter(AdminLanguages_RecyclerViewAdapter.onDataListener onDataListener, ArrayList<String> languagesData){
        this.onDataListener = onDataListener;
        this.languagesData = languagesData;
    }
    @NonNull
    @Override
    public AdminLanguages_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adminlanguages_recycler_view_row, parent, false);
        return new AdminLanguages_RecyclerViewAdapter.ViewHolder(view, onDataListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLanguages_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(languagesData.get(position));
    }

    @Override
    public int getItemCount() {
        return languagesData.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        AdminLanguages_RecyclerViewAdapter.onDataListener onDataListener;
        TextView textView;
        ImageButton button;

        public ViewHolder(@NonNull View itemView, AdminLanguages_RecyclerViewAdapter.onDataListener onDataListener) {
            super(itemView);
            this.onDataListener = onDataListener;
            textView = itemView.findViewById(R.id.adminLanguageText);
            button = itemView.findViewById(R.id.deleteadminLanguageBtn);

            textView.setOnClickListener(this);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.deleteadminLanguageBtn:
                    onDataListener.onLanguageDelete(getAdapterPosition());
                    break;
                case R.id.adminLanguageText:
                    onDataListener.onLanguageClick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
        public TextView getTextView() {
            return textView;
        }
    }
    public interface onDataListener{
        void onLanguageClick(int position);
        void onLanguageDelete(int position);
    }
}
