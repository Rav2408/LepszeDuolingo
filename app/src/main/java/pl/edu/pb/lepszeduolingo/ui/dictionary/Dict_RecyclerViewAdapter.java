package pl.edu.pb.lepszeduolingo.ui.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;
import pl.edu.pb.lepszeduolingo.ui.difficulty1.Diff_RecyclerViewAdapter;



public class Dict_RecyclerViewAdapter extends RecyclerView.Adapter<Dict_RecyclerViewAdapter.ViewHolder> {
    private onWordListener onWordListener;
    private String[] data = {"asdf","fasd","dupa","kupa"};
    public Dict_RecyclerViewAdapter(onWordListener onWordListener){
        this.onWordListener = onWordListener;
    }

    @NonNull
    @Override
    public Dict_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dict_recycler_view_row, parent, false);
        return new Dict_RecyclerViewAdapter.ViewHolder(view, onWordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Dict_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        onWordListener onWordListener;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView, onWordListener onWordListener) {
            super(itemView);
            this.onWordListener = onWordListener;
            imageView = itemView.findViewById(R.id.dictImage);
            textView = itemView.findViewById(R.id.wordTranslationText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWordListener.onWordClick(getAdapterPosition());
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public interface onWordListener{
        void onWordClick(int position);
    }
}
