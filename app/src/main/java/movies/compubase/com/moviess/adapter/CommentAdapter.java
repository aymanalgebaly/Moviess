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
import movies.compubase.com.moviess.model.ListOfComment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolderCommentadapter> {

    private Context context;
    private List<ListOfComment>listOfCommentList;

    public CommentAdapter(List<ListOfComment> listOfCommentList) {
        this.listOfCommentList = listOfCommentList;
    }

    public CommentAdapter(Context context, List<ListOfComment> listOfComments) {
        this.context = context;
        this.listOfCommentList = listOfComments;
    }

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderCommentadapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        context = viewGroup.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.comment_design, viewGroup, false);
        return new ViewHolderCommentadapter(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderCommentadapter viewHolderCommentadapter, int i) {

        ListOfComment listOfComment = listOfCommentList.get(i);

//        Picasso.get().load(listOfComment.).into(viewHolderCommentadapter.img);

        viewHolderCommentadapter.rate_num.setText(listOfComment.getRate());
        viewHolderCommentadapter.comment.setText(listOfComment.getComment());
    }

    @Override
    public int getItemCount() {
        return listOfCommentList != null ? listOfCommentList.size():0;
    }

//    public void setData(List<CommentModel> commentModels) {
//        this.commentModelList = commentModels;
//    }

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
