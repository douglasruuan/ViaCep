package douglas.ruan.viacep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button btCep;
    private Button btRua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btCep = findViewById(R.id.hm_bt_cep);
        btRua = findViewById(R.id.hm_bt_rua);

        btCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Home.this,BuscaCep.class);
                startActivity(it);
            }
        });
    }
}
