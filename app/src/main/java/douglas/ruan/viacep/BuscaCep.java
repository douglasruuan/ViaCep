package douglas.ruan.viacep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import douglas.ruan.viacep.model.CEP;
import douglas.ruan.viacep.services.APIRetrofitService;
import douglas.ruan.viacep.services.CEPDeserializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscaCep extends AppCompatActivity {

    //Widgets
    private EditText etCEP;
    private Button btBuscar;
    private ProgressBar progress;

    private TextView tvRua;
    private TextView tvBairro;
    private TextView tvCidade;
    private TextView tvUf;
    private TextView tvCEP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_cep);

        //Refs
        etCEP = findViewById(R.id.ma_et_cep);
        btBuscar = findViewById(R.id.ma_bt_buscar);
        progress = findViewById(R.id.ma_progress);

        tvRua = findViewById(R.id.ma_tv_rua);
        tvBairro = findViewById(R.id.ma_tv_bairro);
        tvCEP = findViewById(R.id.ma_tv_cep);
        tvCidade = findViewById(R.id.ma_tv_cidade);
        tvUf = findViewById(R.id.ma_tv_uf);

        progress.setVisibility(View.GONE);
        Gson g = new GsonBuilder().registerTypeAdapter(CEP.class, new CEPDeserializer()).create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create(g))
                .build();

        final APIRetrofitService service = retrofit.create(APIRetrofitService.class);


        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCEP.getText().toString().isEmpty()){

                    toast("informe o CEP");
                }else{
                    progress.setVisibility(View.VISIBLE);
                    Call<CEP> call = service.getEnderecoByCEP(etCEP.getText().toString());

                    call.enqueue(new Callback<CEP>() {
                        @Override
                        public void onResponse(Call<CEP> call, Response<CEP> response) {
                            if(response.isSuccessful()){

                                CEP cep = response.body();
                                tvCEP.setText((cep.getCep()));
                                tvRua.setText(cep.getLogradouro());
                                tvBairro.setText(cep.getBairro());
                                tvCidade.setText(cep.getLocalidade());
                                tvUf.setText(cep.getUf());

                                toast("CEP DIGITADO, BUSCADO ! ");

                            }else{

                                toast("ERRO! "+response.message());
                            }//fecha else
                            progress.setVisibility(View.GONE);
                        }


                        @Override
                        public void onFailure(Call<CEP> call, Throwable t) {
                            toast("ERRO ONFAILURE: "+t.getMessage());
                            progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }//fecha oncreate

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT).show();
    }
}//fecha classe
