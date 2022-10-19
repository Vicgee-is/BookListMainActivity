package cn.edu.jnu.supershopper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputBookItemActivity extends AppCompatActivity {

    public static final int RESULT_CODE_SUCCESS = 666;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_book_item);

        position =this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
       // Double price=this.getIntent().getDoubleExtra("price",0);

        EditText editTextTitle=findViewById(R.id.edittext_book_item_title);
        //EditText editTextPrice=findViewById(R.id.edittext_book_item_price);

        if(null!=title)
        {
            editTextTitle.setText(title);
           // editTextPrice.setText(price.toString());
        }
        Button buttonNo=findViewById(R.id.button_no);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);
                InputBookItemActivity.this.finish();
            }
        });

        Button buttonOk=findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("title", editTextTitle.getText().toString());
                //  double price=Double.parseDouble( editTextPrice.getText().toString());
                // bundle.putDouble("price",price);
                bundle.putInt("position", position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS, intent);
                InputBookItemActivity.this.finish();
            }


        });
    }
}