package cn.edu.jnu.supershopper;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.edu.jnu.supershopper.data.BookItem;

public class MainActivity extends AppCompatActivity {

    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;
    private ArrayList<BookItem> bookItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    int []bookPicture=new int[]{R.drawable.book_1, R.drawable.book_2, R.drawable.book_3};
    String []bookNames=new String[]{" 软件项目管理案例教程（第4版）"," 创新工程实践"," 信息安全数学基础（第2版）"};
    double []bookPrice=new double[]{100.00,121.00,123.00};

    private ActivityResultLauncher<Intent> addDataLauncher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            ,result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== InputBookItemActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title= bundle.getString("title");
                        double price=bundle.getDouble("price");
                        bookItems.add(1, new BookItem(title,price,R.drawable.book_1) );
                        mainRecycleViewAdapter.notifyItemInserted(1);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycleview_main);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        bookItems = new ArrayList<>();

        for(int i=1;i<20;++i)
        {
            bookItems.add(new BookItem(bookNames[i%3],bookPrice[i%3],bookPicture[i%3]));
        }
        mainRecycleViewAdapter= new MainRecycleViewAdapter(bookItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case MENU_ID_ADD:
                Intent intent=new Intent(this, InputBookItemActivity.class);
                addDataLauncher.launch(intent);
                break;
            case MENU_ID_UPDATE:
                bookItems.get(item.getOrder()).setTitle("软件项目管理案例教程（第4版）");
                mainRecycleViewAdapter.notifyItemChanged(item.getOrder());
                break;
            case MENU_ID_DELETE:
                AlertDialog alertDialog;
                alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                bookItems.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {

        private ArrayList<BookItem> localDataSet;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;

            public TextView getTextViewPrice() {
                return textViewPrice;
            }

            private final TextView textViewPrice;
            private final ImageView imageViewImage;

            public ViewHolder(View view) {
                super(view);
                // Define click listener for the ViewHolder's View

                imageViewImage = view.findViewById(R.id.imageview_item_image);
                textViewTitle = view.findViewById(R.id.textview_item_caption);
                textViewPrice = view.findViewById(R.id.textview_item_price);

                view.setOnCreateContextMenuListener(this);
            }

            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageViewImage() {
                return imageViewImage;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"Add "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"Update "+getAdapterPosition());
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"Delete "+getAdapterPosition());
            }
        }

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used
         * by RecyclerView.
         */
        public MainRecycleViewAdapter(ArrayList<BookItem> dataSet) {
            localDataSet = dataSet;
        }

        // Create new views (invoked by the layout manager)
        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main, viewGroup, false);

            return new ViewHolder(view);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.getTextViewTitle().setText(localDataSet.get(position).getTitle());
            viewHolder.getTextViewPrice().setText(localDataSet.get(position).getPrice().toString());
            viewHolder.getImageViewImage().setImageResource(localDataSet.get(position).getResourceId());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
}