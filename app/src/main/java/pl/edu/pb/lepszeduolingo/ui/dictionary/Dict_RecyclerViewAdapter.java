package pl.edu.pb.lepszeduolingo.ui.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import pl.edu.pb.lepszeduolingo.R;

public class Dict_RecyclerViewAdapter extends RecyclerView.Adapter<Dict_RecyclerViewAdapter.ViewHolder> {
    Context context;

    public Dict_RecyclerViewAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public Dict_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.dict_recycler_view_row, parent, false);
        return new Dict_RecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Dict_RecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.dictImage);
            textView = itemView.findViewById(R.id.dictWord);
            // TODO: here set image and base word
        }
    }
}
