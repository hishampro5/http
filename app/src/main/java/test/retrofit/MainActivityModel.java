package test.retrofit;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Response;
import webconnect.com.webconnect.QueryMap;
import webconnect.com.webconnect.WebConnect;
import webconnect.com.webconnect.listener.OnWebCallback;

/**
 * Created by clickapps on 22/12/17.
 */

public class MainActivityModel extends AndroidViewModel {
    public static final String ENDPOINT_GET = "offers";
    public static final String ENDPOINT_POST = "users";
    public static final String ENDPOINT_PUT = "users/740";
    public static final String ENDPOINT_BASE = "https://reqres.in/api/";
    //    public static final String ENDPOINT_BASE = "http://api.dev.moh.clicksandbox1.com:8080/v1/";
    private Context activity;

//    private MainActivityModel(Activity activity) {
//        this.activity = activity;
//    }

    private MutableLiveData<Object> get = new MutableLiveData<Object>();
    private MutableLiveData<Object> post = new MutableLiveData<Object>();
    private MutableLiveData<Object> put = new MutableLiveData<Object>();
    private MutableLiveData<Object> delete = new MutableLiveData<Object>();

    public MainActivityModel(@NonNull Application application) {
        super(application);
        activity = application;
    }

    public LiveData<Object> getGet() {
        return get;
    }

    public LiveData<Object> getPost() {
        return post;
    }

    public LiveData<Object> getPut() {
        return put;
    }

    public LiveData<Object> getDelete() {
        return delete;
    }
//    D/OkHttp: --> GET http://api.qa.leasing.clicksandbox.com/v1/app/leases
//            01-17 12:10:53.411 6765-24325/com.brickspms D/OkHttp: slug: default
//01-17 12:10:53.411 6765-24325/com.brickspms D/OkHttp: Auth-Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0ZW5hbnRfaWQiOjEwODMsImlhdCI6IjIwMTgtMDEtMTIgMDc6NTg6NTAgVVRDIn0.mXkySHf71fa3vdLwUWaIqoqd5nUR2Z3dJ1INq5t4Clo
//01-17 12:10:53.412 6765-24325/com.brickspms D/OkHttp:

    public void get() {
        QueryMap<String, String> headerMap = new QueryMap<>();
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6NDM3ODMsIm5hbWUiOiJjZmNnZyBHZ2dnIGNjY2MgY2NjY2MiLCJlbWFpbCI6ImFsbWFycmlAbW9oLmdvdi5zYSIsIm1vYmlsZSI6IjUzMDgwMzA5MSIsInJvbGUiOiJlbXBsb3llZSIsImFjY2VzcyI6Im1vYmlsZSIsImRvbWFpbiI6ImFsbCIsImlhdCI6MTU0NjUwMzQ2MSwiZXhwIjoxNTQ5MDk1NDYxfQ.4OhtjSj5b0u7h57t3_9DEBXgkYsqo6nVLJ5eemDYg2o");
        headerMap.put("Authorization", "12");
        List<Call> callList = new ArrayList<>();
        Call call1 = WebConnect.with(this.activity, "requests")
                .get()
                .queryParam(headerMap)
                .headerParam(headerMap)
                .baseUrl("https://api.hrs.staging.clicksandbox.com/v1/")
                .timeOut(100L, 50L)
                .queue();

        callList.add(call1);
        headerMap.put("Authorization", "13");
        Call call2 = WebConnect.with(this.activity, "requests1")
                .get()
                .queryParam(headerMap)
                .headerParam(headerMap)
                .baseUrl("https://api.hrs.staging.clicksandbox.com/v1/")
                .timeOut(100L, 50L)
                .queue();

        callList.add(call2);

        Observable.create((ObservableOnSubscribe<Call>) emitter -> {
            for (Call c : callList) {
                emitter.onNext(c);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Call, ObservableSource<String>>) call -> new Simple(call)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String o) {
                        Log.i("Main Activity Model", "response" + o);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    class Simple extends Observable<String> {
        private Call call;

        Simple(Call call) {
            this.call = call;
            Log.i(Simple.class.getSimpleName(), "Request = " + call.request().toString());
        }

        @Override
        protected void subscribeActual(Observer<? super String> observer) {
            try {
                Response response = call.execute();
                if (response.body() != null) {
                    String res = response.body().string();
                    observer.onNext(res);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> post() {
        Map<String, String> requestMap = new LinkedHashMap<String, String>();
        requestMap.put("locale", "en");
        requestMap.put("name", "manager1");
        requestMap.put("birth_date", "18/08/1987");
        requestMap.put("gender", "male");
        Map<String, String> headerMap = new LinkedHashMap<String, String>();
        headerMap.put("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MTQsIm5hbWUiOiJHdXJ1IiwiZW1haWwiOiJndXJwcmVldDJAY2xpY2thcHBzLmNvIiwibW9iaWxlIjoiODI4NzYyMTIyOCIsImltYWdlIjoiL2RlZmF1bHRfbG9nby5qcGciLCJpYXQiOjE1MjExODAwOTIsImV4cCI6MTUyMzc3MjA5Mn0.Cc4dOzVC3NipXfVOJdRE29-GrtO5H0dgC3GSABiTYTA");
        WebConnect.with(this.activity, ENDPOINT_POST)
                .put()
                .multipart()
                .multipartParam(requestMap)
                .timeOut(100L, 50L)
                .headerParam(headerMap)
                .callback(new OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId) {
                        if (object != null) {
                            post.postValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        post.setValue(object);
                    }
                })
                .connect();
        return requestMap;
    }

    public void put() {
        Map<String, String> requestMap = new LinkedHashMap<String, String>();
        requestMap.put("locale", "Amit Singh");
        requestMap.put("name", "manager");
        requestMap.put("birth_date", "18/08/1987");
        requestMap.put("gender", "male");
        WebConnect.with(activity, ENDPOINT_PUT)
                .put()
                .formDataParam(requestMap)
                .callback(new OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId) {
                        if (object != null) {
                            put.setValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        put.setValue(object);
                    }
                }).connect();
    }

    public void delete() {
        Map<String, String> requestMap = new LinkedHashMap<String, String>();
        requestMap.put("name", "Amit Singh");
        requestMap.put("job", "manager");
        WebConnect.with(activity, ENDPOINT_PUT)
                .download(new File("/test"))
                .get()
                .success(file -> {
                    Log.i(getClass().getSimpleName(), "success = " + file);
                })
                .error(msg -> {
                    Log.i(getClass().getSimpleName(), "error = " + msg);
                })
                .callback(new OnWebCallback() {
                    @Override
                    public <T> void onSuccess(@Nullable T object, int taskId) {
                        if (object != null) {
                            delete.postValue(object);
                        }
                    }

                    @Override
                    public <T> void onError(@Nullable T object, String error, int taskId) {
                        delete.postValue(object);
                    }
                }).connect();
    }


    public static class MainActivityModelFactory implements ViewModelProvider.Factory {
        private Application activity;

        public MainActivityModelFactory(Application activity) {
            this.activity = activity;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainActivityModel(activity);
        }
    }

}
