package movies.compubase.com.moviess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import movies.compubase.com.moviess.R;
import movies.compubase.com.moviess.model.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderCommentadapter> {

    private Context context;
    private List<CommentModel>commentModelList;

    public CommentAdapter(Context context, List<CommentModel> commentModelList) {
        this.context = context;
        this.commentModelList = commentModelList;
    }

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderCommentadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_design, viewGroup, false);
        return new ViewHolderCommentadapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCommentadapter viewHolderCommentadapter, int i) {

        CommentModel commentModel = commentModelList.get(i);

        Picasso.get().load(commentModel.getImg()).into(viewHolderCommentadapter.img);

        viewHolderCommentadapter.rate_num.setText(Integer.toString(commentModel.getRat()));
        viewHolderCommentadapter.comment.setText(commentModel.getComment());
    }

    @Override
    public int getItemCount() {
        return commentModelList != null ? commentModelList.size():0;
    }

    public void setData(List<CommentModel> commentModels) {
        this.commentModelList = commentModels;
    }

    public class ViewHolderCommentadapter extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView rate_num,comment;

        public ViewHolderCommentadapter(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_comment);
            rate_num = itemView.findViewById(R.id.rate_num_txt_comment);
            comment = itemView.findViewById(R.id.edit_comment);
        }
    }
}
