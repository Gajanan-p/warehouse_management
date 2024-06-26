package com.ssinfomate.warehousemanagement.ui.login;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ssinfomate.warehousemanagement.ui.setting.ZebraPrinterDialog;
import com.ssinfomate.warehousemanagement.webservices.login.User;
import com.ssinfomate.warehousemanagement.webservices.login.UserModel;
import com.ssinfomate.warehousemanagement.MainActivity;
import com.ssinfomate.warehousemanagement.R;
import com.ssinfomate.warehousemanagement.ui.dialog.DialogListener;
import com.ssinfomate.warehousemanagement.utils.AppPreference;
import com.ssinfomate.warehousemanagement.utils.AppSetting;
import com.ssinfomate.warehousemanagement.webservices.WebServiceClient;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener, DialogListener {

    UserModel userModel;
    NavController navController;
    private ProgressDialog progressDialog;

    TextInputEditText appCompatEditTextLoginName;
    TextInputEditText appCompatEditTextPassword;
    AppCompatButton appCompatButtonLogin;

    AppSetting appSetting;
    ZebraPrinterDialog zebraPrinterDialog;
    JSONObject jsonObject=new JSONObject();
    static String TAG_SERVER_URL="TAG_SERVER_URL";

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.login_fragment, container, false);
        appCompatEditTextLoginName=view.findViewById(R.id.edit_user_id);
        appCompatEditTextPassword=view.findViewById(R.id.edit_password);
        appCompatButtonLogin=view.findViewById(R.id.button_login);
        appCompatButtonLogin.setOnClickListener(this);
        userModel=AppPreference.getLoginDataPreferences(getContext());
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        if(userModel!=null){
            if(userModel.getIsLogin()==1){
                navController.navigate(R.id.action_nav_Login_to_nav_home);
            }
        }

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }
    public void onLoginClicked(View view){
        if (appSetting==null){
        checkCredentialWithServer();
        }else
            Toast.makeText(getContext(),"Select Company ",Toast.LENGTH_SHORT).show();
    }

    void checkCredentialWithServer(){
        progressDialog = createProgressDialog(getActivity());
        if(!TextUtils.isEmpty(appCompatEditTextLoginName.getText())
                && !TextUtils.isEmpty(appCompatEditTextPassword.getText())){
            User user=new User(appCompatEditTextLoginName.getText().toString(),
                    appCompatEditTextPassword.getText().toString());
            String BASE_URL=appSetting.getSettingServerURL();
            Call<ArrayList<UserModel>> listUser= WebServiceClient.loginService(BASE_URL).listUser(user);
            listUser.enqueue(new Callback<ArrayList<UserModel>>() {
                @Override
                public void onResponse(Call<ArrayList<UserModel>> call, Response<ArrayList<UserModel>> response) {
                    ArrayList<UserModel> userModels=response.body();
//                    SharedPreferences.Editor editor=AppPreference.sharedPreferences.edit();
                    if(userModels!=null){
                        UserModel userModel=userModels.get(0);
                        AppPreference.setLoginDataPreferences(getContext(),userModel);
                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                        navController.navigate(R.id.nav_home);
                        Intent intent=new Intent(getActivity(),MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        Toast.makeText(getContext(),userModels.get(0).getMsg(),Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(getContext(),"User credential not match",Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ArrayList<UserModel>> call, Throwable t) {
                    Log.e("Login",t.getStackTrace().toString());
                    progressDialog.dismiss();
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        appSetting= AppPreference.getSettingDataPreferences(getContext());
        if(appSetting==null){
            try {
                appSetting=new AppSetting();
                jsonObject.put("type",TAG_SERVER_URL);
                jsonObject.put("title","Enter Server URL");
                initDialog(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            if(appSetting.getSettingServerURL()==null || appSetting.getSettingServerURL()==""){
                try {
                    jsonObject.put("type",TAG_SERVER_URL);
                    jsonObject.put("title","Enter Server URL");
                    initDialog(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                checkCredentialWithServer();
            }
        }
    }

    public ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
           // dialog.show();
        } catch (Exception e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.blue(100)));
        dialog.setContentView(R.layout.dialog_progress_layout);
        return dialog;
    }

    @Override
    public void initDialog(JSONObject jsonObject) {
        zebraPrinterDialog=new ZebraPrinterDialog(getContext(),this,jsonObject);
        zebraPrinterDialog.show();
    }

    @Override
    public void onCancelClicked() {
        zebraPrinterDialog.dismiss();
    }

    @Override
    public void onOkClicked(String s) {
        try {
            jsonObject=new JSONObject(s);
            if(TAG_SERVER_URL.equals(jsonObject.getString("type"))){
                appSetting.setSettingServerURL(jsonObject.getString("value"));
                AppPreference.setSettingDataPreferences(getContext(),appSetting);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        dismissDialog();
    }

    @Override
    public void dismissDialog() {
    zebraPrinterDialog.dismiss();
    }


}