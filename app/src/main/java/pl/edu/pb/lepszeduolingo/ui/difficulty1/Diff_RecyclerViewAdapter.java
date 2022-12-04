package pl.edu.pb.lepszeduolingo.ui.difficulty1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;

public class Diff_RecyclerViewAdapter  extends RecyclerView.Adapter<Diff_RecyclerViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public Diff_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.diff_recycler_view_row, parent, false);
        return new Diff_RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Diff_RecyclerViewAdapter.ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return 5;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.diffCategory);
            // TODO: here set image and base word
        }
    }
}
