package com.example.agenda.ui.addproduct;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.agenda.MainActivity;
import com.example.agenda.R;
import com.example.agenda.database.AgendaViewModel;
import com.example.agenda.database.Product;
import com.example.agenda.databinding.ActivityAddProductBinding;
import com.example.agenda.ui.home.HomeFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddProductActivity extends AppCompatActivity {

    ActivityAddProductBinding binding;
    AgendaViewModel agendaViewModel;
    String name,model_number;
    double buying_price,selling_price;
    long number_of_pieces;
    byte[] image;
    byte[] updateImage;
    Product updateProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        updateProduct = (Product) intent.getSerializableExtra(HomeFragment.PRODUCT_KEY);

        agendaViewModel= new ViewModelProvider(this).get(AgendaViewModel.class);

        if(updateProduct != null){
            updateImage=updateProduct.getImage();
            if(updateImage==null){
                binding.addProductIv.setImageResource(R.drawable.ic_image_hint);
            }else{
                Bitmap bitmap= BitmapFactory.decodeByteArray(updateImage,0,updateImage.length);
                binding.addProductIv.setImageBitmap(bitmap);
            }
            binding.addProductEtName.setText(updateProduct.getName());
            binding.addProductEtModelNumber.setText(updateProduct.getModel_number());
            binding.addProductEtBuyingPrice.setText(String.valueOf(updateProduct.getBuying_price()));
            binding.addProductEtSellingPrice.setText(String.valueOf(updateProduct.getSelling_price()));
            binding.addProductEtNumberOfPieces.setText(String.valueOf(updateProduct.getNumber_of_pieces()));
            binding.addProductBtnSave.setText(R.string.add_product_btn_update);

        }

        ActivityResultLauncher<String> arl=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        if (result != null){
                            binding.addProductIv.setImageURI(result);
                            try {
                                image=getBytes(result);
                                image=imagemTratada(image);
                                updateImage=getBytes(result);
                                updateImage=imagemTratada(updateImage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        binding.addProductBtnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch("image/*");
            }
        });

        binding.addProductFabPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addProductEtNumberOfPieces.getText().toString().isEmpty()){
                    binding.addProductEtNumberOfPieces.setText(String.valueOf(0));
                }else {
                    long x = Long.parseLong(binding.addProductEtNumberOfPieces.getText().toString());
                    x++;
                    binding.addProductEtNumberOfPieces.setText(String.valueOf(x));
                }
            }
        });

        binding.addProductFabMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addProductEtNumberOfPieces.getText().toString().isEmpty()){
                    binding.addProductEtNumberOfPieces.setText(String.valueOf(0));
                }else {
                    long x = Long.parseLong(binding.addProductEtNumberOfPieces.getText().toString());
                    if (x > 0)
                        x--;
                    binding.addProductEtNumberOfPieces.setText(String.valueOf(x));
                }
            }
        });

        binding.addProductBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkEmbtyFields(binding.addProductEtName,binding.addProductEtModelNumber,binding.addProductEtBuyingPrice,binding.addProductEtSellingPrice,binding.addProductEtNumberOfPieces)){
                    Toast.makeText(AddProductActivity.this, R.string.add_product_toast_enter_data, Toast.LENGTH_SHORT).show();
                }else {
                    name = binding.addProductEtName.getText().toString();
                    model_number = binding.addProductEtModelNumber.getText().toString();
                    buying_price = Double.parseDouble(binding.addProductEtBuyingPrice.getText().toString());
                    selling_price = Double.parseDouble(binding.addProductEtSellingPrice.getText().toString());
                    number_of_pieces = Long.parseLong(binding.addProductEtNumberOfPieces.getText().toString());

                    if(binding.addProductBtnSave.getText().toString().equals("Update")||binding.addProductBtnSave.getText().toString().equals("تعديل")){
                        updateProduct.setName(name);
                        updateProduct.setModel_number(model_number);
                        updateProduct.setBuying_price(buying_price);
                        updateProduct.setSelling_price(selling_price);
                        updateProduct.setNumber_of_pieces(number_of_pieces);
                        updateProduct.setImage(updateImage);
                        agendaViewModel.updateProduct(updateProduct);
                        Toast.makeText(AddProductActivity.this, R.string.add_product_toast_product_updated, Toast.LENGTH_SHORT).show();
                    }else {
                        Product product = new Product(name,model_number,buying_price,selling_price,number_of_pieces,image);
                        agendaViewModel.insertProduct(product);

                        //Toast.makeText(AddProductActivity.this, R.string.add_product_toast_product_inserted, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(AddProductActivity.this, product.getProduct_id()+"", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }

    public byte[] getBytes(Uri uri) throws IOException {
        InputStream inputStream=getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        int bufferSize=1024;
        byte[] buffer=new byte[bufferSize];

        int len =0;
        while((len=inputStream.read(buffer))!= -1){
            byteBuffer.write(buffer,0,len);
        }

        return byteBuffer.toByteArray();
    }

    public boolean checkEmbtyFields(TextInputEditText... editText){
        for (int i = 0; i < editText.length; i++){
            if(editText[i].getText().toString().isEmpty())
                return true;
        }
        return false;
    }

    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }
}