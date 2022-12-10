package pl.edu.pb.lepszeduolingo.ui.difficulty1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;

public class Diff_RecyclerViewAdapter  extends RecyclerView.Adapter<Diff_RecyclerViewAdapter.ViewHolder> {
    private String[] data = {"asdf","fasd","dupa","kupa","pupa"};
    private onCategoryListener onCategoryListener;
    public Diff_RecyclerViewAdapter(onCategoryListener onCategoryListener){
        this.onCategoryListener = onCategoryListener;
    }
    @NonNull
    @Override
    public Diff_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.diff_recycler_view_row, parent, false);
        return new Diff_RecyclerViewAdapter.ViewHolder(view, onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Diff_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getTextView().setText(data[position]);
    }
    @Override
    public int getItemCount() {
        return 5;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;
        onCategoryListener onCategoryListener;

        public ViewHolder(@NonNull View itemView, onCategoryListener onCategoryListener) {
            super(itemView);
            this.onCategoryListener = onCategoryListener;
            textView = itemView.findViewById(R.id.wordTranslationText);
            // TODO: here set image and base word

            itemView.setOnClickListener(this);
        }
        public TextView getTextView() {
            return textView;
        }
        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }
    public interface onCategoryListener{
        void onCategoryClick(int position);
    }
}
